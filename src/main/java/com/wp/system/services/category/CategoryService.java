package com.wp.system.services.category;

import com.wp.system.entity.BankTransactionType;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.repository.sber.SberTransactionRepository;
import com.wp.system.repository.tinkoff.TinkoffTransactionRepository;
import com.wp.system.repository.tochkaa.TochkaTransactionRepository;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.SystemImageTag;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.services.image.ImageService;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.bill.BillBalanceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private BillTransactionRepository billTransactionRepository;

    @Autowired
    private SberTransactionRepository sberTransactionRepository;

    @Autowired
    private TochkaTransactionRepository tochkaTransactionRepository;

    @Autowired
    private TinkoffTransactionRepository tinkoffTransactionRepository;

    public List<Category> getUserCategories() {
        User user = authHelper.getUserFromAuthCredentials();

        return categoryRepository.getAllUserCategories(user.getId());
    }

    public Category createCategory(CreateCategoryRequest request) {
        User user = this.userService.getUserById(request.getUserId());

        SystemImage image = null;

        if(request.getIcon() != null) {
            image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image Tag", HttpStatus.BAD_REQUEST);
        }

        Category category = new Category(request.getName(), request.getColor(), request.getDescription(), user, image);

        category.setOnlyForEarn(request.getOnlyForEarn());
        category.setCategoryLimit(request.getCategoryLimit());

        categoryRepository.save(category);

        return category;
    }

    @Scheduled(cron="0 0 0 * * *", zone="Europe/Istanbul")
    public void cleanLimits() {
        categoryRepository.findByResetDataDateGreaterThanEqual(Timestamp.from(Instant.now())).forEach((item) -> {
            item.setCategorySpend(0.0);
            item.setCategoryEarn(0.0);
            item.setPercentsFromLimit(0.0);
        });
    }

    public Category editCategory(EditCategoryRequest request, UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        if(request.getDescription() != null && !category.getDescription().equals(request.getDescription()))
            category.setDescription(request.getDescription());

        if(request.getName() != null && !category.getName().equals(request.getName()))
            category.setName(request.getName());

        if(request.getColor() != null && !category.getColor().equals(request.getColor()))
            category.setColor(request.getColor());

        if(request.getOnlyForEarn() != null) {
            category.setOnlyForEarn(request.getOnlyForEarn());
        }

        if(request.getIcon() != null) {
            SystemImage image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image tag", HttpStatus.BAD_REQUEST);

            category.setIcon(image);
        }

        if(request.getCategoryLimit() != null && category.getCategoryLimit() != request.getCategoryLimit()) {
            if(request.getCategoryLimit() != 0) {
                category.setCategorySpend(0.0);
                category.setPercentsFromLimit(0.0);

                category.setResetDataDate(
                        Instant.from(Instant.now()
                                .atZone(ZoneId.systemDefault())
                                .plus(1, ChronoUnit.MONTHS)
                                .minus(
                                        Instant.now().atZone(ZoneId.systemDefault()).getDayOfMonth() - 1, ChronoUnit.DAYS
                                )).truncatedTo(ChronoUnit.DAYS));

                Instant transactionsDate = Instant.from(category.getResetDataDate().atZone(ZoneId.systemDefault()).minus(1, ChronoUnit.MONTHS));

                billTransactionRepository.findByCategoryIdAndCreateAtGreaterThanEqual(categoryId, Timestamp.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getAction() == BillBalanceAction.WITHDRAW) {
                                category.setCategorySpend(category.getCategorySpend() + item.getSum());
                                category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100);
                            }
                        });

                sberTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Timestamp.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == BankTransactionType.SPEND) {
                                category.setCategorySpend(category.getCategorySpend() +
                                        (Double.parseDouble(item.getAmount().getAmount() + "." + item.getAmount().getCents())));
                                category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100);
                            }
                        });

                tinkoffTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Timestamp.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == BankTransactionType.SPEND) {
                                category.setCategorySpend(category.getCategorySpend() +
                                        (Double.parseDouble(item.getAmount().getAmount() + "." + item.getAmount().getCents())));
                                category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100);
                            }
                        });

                tochkaTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Timestamp.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == BankTransactionType.SPEND) {
                                category.setCategorySpend(category.getCategorySpend() +
                                        (Double.parseDouble(item.getAmount().getAmount() + "." + item.getAmount().getCents())));
                                category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100);
                            }
                        });
            }
            category.setCategoryLimit(request.getCategoryLimit());
        }

        categoryRepository.save(category);

        return category;
    }

    @Transactional
    public Category removeCategory(UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        category.setUser(null);
        category.setIcon(null);
        this.categoryRepository.delete(category);

        return category;
    }

    public Category getCategoryById(UUID id) {
        Optional<Category> foundCategory = this.categoryRepository.findById(id);
        User user = authHelper.getUserFromAuthCredentials();

        if(foundCategory.isEmpty())
            throw new ServiceException("Category not found", HttpStatus.BAD_REQUEST);

        if(!foundCategory.get().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        return foundCategory.get();
    }
}
