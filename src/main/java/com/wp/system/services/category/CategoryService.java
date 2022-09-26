package com.wp.system.services.category;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.repository.sber.SberTransactionRepository;
import com.wp.system.repository.tinkoff.TinkoffTransactionRepository;
import com.wp.system.repository.tochkaa.TochkaTransactionRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.category.AddCategoryToFavoriteRequest;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.SystemImageTag;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.services.image.ImageService;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.bill.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    @Autowired
    private UserRepository userRepository;

//    @Scheduled(cron = "0 1 1 * * *")
//    public void updateCategoriesStatistic() {
//        Page<User> startData = userRepository.findAll(PageRequest.of(0, 50));
//
//        for (int i = 0; i < startData.getTotalPages(); i++) {
//            Page<User> users = userRepository.findAll(PageRequest.of(0, 50));
//
//            for (User u : users) {
//                List<Category> categories = categoryRepository.getAllUserCategories(u.getId());
//
//                Instant firstDate = Instant.now();
//                Instant secondDate = Instant.now().minus(3, ChronoUnit.MONTHS);
//
//                for (Category c : categories) {
//                    Page<BillTransaction> tStartData = billTransactionRepository.findByCategoryIdAndCreateAtBetween(
//                            c.getId(),
//                            firstDate,
//                            secondDate,
//                            PageRequest.of(0, 50)
//                    );
//
//                    BigDecimal earnSum = BigDecimal.ZERO;
//                    BigDecimal spendSum = BigDecimal.ZERO;
//                    Long earnCounter = 0L;
//                    Long spendCounter = 0L;
//
//                    for (int j = 0; j < tStartData.getTotalPages(); j++) {
//                        Page<BillTransaction> transactions = billTransactionRepository.findByCategoryIdAndCreateAtBetween(
//                                c.getId(),
//                                firstDate,
//                                secondDate,
//                                PageRequest.of(j, 50)
//                        );
//
//                        for (BillTransaction t : transactions) {
//                            if(t.getAction() == TransactionType.DEPOSIT) {
//                                earnSum = earnSum.add(t.getSum());
//                                earnCounter++;
//                            } else {
//                                spendSum = spendSum.add(t.getSum());
//                                spendCounter++;
//                            }
//                        }
//                    }
//
//                    c.setSpendStatistic(spendSum.divide(BigDecimal.valueOf(spendCounter), 2, RoundingMode.CEILING));
//                    c.setEarnStatistic(earnSum.divide(BigDecimal.valueOf(earnCounter), 2, RoundingMode.CEILING));
//
//                    categoryRepository.save(c);
//                }
//            }
//        }
//    }

    public Category removeCategoryFromFavorite(UUID id) {
        User user = authHelper.getUserFromAuthCredentials();

        Category category = categoryRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> {
            throw new ServiceException("Категория не найдена", HttpStatus.NOT_FOUND);
        });

        if(category.getFavorite() == true) {
            category.setFavorite(false);
        }

        categoryRepository.save(category);

        return category;
    }

    public Category addCategoryToFavorite(AddCategoryToFavoriteRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), user.getId()).orElseThrow(() -> {
            throw new ServiceException("Категория не найдена", HttpStatus.NOT_FOUND);
        });

        if(category.getFavorite() != true) {
            category.setFavorite(true);
        }

        categoryRepository.save(category);

        return category;
    }

    public List<Category> getUserFavoriteCategories() {
        User user = authHelper.getUserFromAuthCredentials();

        return categoryRepository.findByUserIdAndFavorite(user.getId(), true);
    }


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

        category.setForEarn(request.getForEarn());
        category.setForSpend(request.getForSpend());

        if(request.getCategoryLimit() != null)
            category.setCategoryLimit(request.getCategoryLimit());

        categoryRepository.save(category);

        return category;
    }

    @Scheduled(cron="0 0 0 * * *", zone="Europe/Istanbul")
    public void cleanLimits() {
        categoryRepository.findByResetDataDateGreaterThanEqual(Timestamp.from(Instant.now())).forEach((item) -> {
            item.setCategorySpend(new BigDecimal(0));
            item.setCategoryEarn(new BigDecimal(0));
            item.setPercentsFromLimit(new BigDecimal(0));
        });
    }

    public Category editCategory(EditCategoryRequest request, UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        if(request.getDescription() != null)
            category.setDescription(request.getDescription());

        if(request.getName() != null && !category.getName().equals(request.getName()))
            category.setName(request.getName());

        if(request.getColor() != null && !category.getColor().equals(request.getColor()))
            category.setColor(request.getColor());

        if(request.getForEarn() != null) {
            category.setForEarn(request.getForEarn());
        }

        if(request.getForSpend() != null) {
            category.setForSpend(request.getForSpend());
        }

        if(request.getIcon() != null) {
            SystemImage image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image tag", HttpStatus.BAD_REQUEST);

            category.setIcon(image);
        }

        if(request.getCategoryLimit() != null && category.getCategoryLimit().compareTo(request.getCategoryLimit()) != 0) {
            if(request.getCategoryLimit().compareTo(BigDecimal.ZERO) != 0) {
                category.setCategorySpend(BigDecimal.ZERO);
                category.setPercentsFromLimit(BigDecimal.ZERO);
                category.setCategoryLimit(request.getCategoryLimit());

                category.setResetDataDate(
                        Instant.from(Instant.now()
                                .atZone(ZoneId.systemDefault())
                                .plus(1, ChronoUnit.MONTHS)
                                .minus(
                                        Instant.now().atZone(ZoneId.systemDefault()).getDayOfMonth() - 1, ChronoUnit.DAYS
                                )).truncatedTo(ChronoUnit.DAYS));

                ZonedDateTime transactionsDate = category.getResetDataDate().atZone(ZoneId.systemDefault()).minus(1, ChronoUnit.MONTHS);

                billTransactionRepository.findByCategoryIdAndCreateAtGreaterThanEqual(categoryId, Instant.from(transactionsDate))
                        .forEach((item) -> {
                            System.out.println(item.getAction());
                            if(item.getAction() == TransactionType.WITHDRAW) {
                                category.setCategorySpend(category.getCategorySpend().add(item.getSum()));
                                category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING).multiply(new BigDecimal("100"))));
                            }
                        });

                sberTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Instant.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == TransactionType.WITHDRAW) {
                                category.setCategorySpend(category.getCategorySpend().add(
                                        (item.getAmount())));
                                category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100")));
                            }
                        });

                tinkoffTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Instant.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == TransactionType.WITHDRAW) {
                                category.setCategorySpend(category.getCategorySpend().add
                                        (item.getAmount()));
                                category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100")));
                            }
                        });

                tochkaTransactionRepository.findByCategoryIdAndDateGreaterThanEqual(categoryId, Instant.from(transactionsDate))
                        .forEach((item) -> {
                            if(item.getTransactionType() == TransactionType.WITHDRAW) {
                                category.setCategorySpend(category.getCategorySpend().add(item.getAmount()));
                                category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100")));
                            }
                        });
            } else {
                category.setPercentsFromLimit(BigDecimal.ZERO);
                category.setCategorySpend(null);
            }
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
