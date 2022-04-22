package com.wp.system.services.admin;

import com.wp.system.config.security.UserAuthDetails;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.activity.Activity;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserEmail;
import com.wp.system.entity.user.UserRole;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.activity.ActivityRepository;
import com.wp.system.repository.bill.BillRepository;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.repository.loyalty.LoyaltyCardRepository;
import com.wp.system.repository.sber.SberCardRepository;
import com.wp.system.repository.subscription.SubscriptionRepository;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.repository.tochkaa.TochkaCardRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.logging.CreateAdminLogRequest;
import com.wp.system.request.user.EditUserRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.bill.BillService;
import com.wp.system.services.user.UserRoleService;
import com.wp.system.utils.CurrencySingleton;
import com.wp.system.utils.CurrencySingletonCourse;
import com.wp.system.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TinkoffCardRepository tinkoffCardRepository;

    @Autowired
    private BillTransactionRepository billTransactionRepository;

    @Autowired
    private TochkaCardRepository tochkaCardRepository;

    @Autowired
    private SberCardRepository sberCardRepository;

    @Autowired
    private CurrencySingleton currencySingleton;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public PagingResponse<UserDTO> getPagedUsers(int page, int pageSize) {
        Page<User> pagedUsers = userRepository.findAll(PageRequest.of(page, pageSize));
        return new PagingResponse<>(pagedUsers.stream().map(UserDTO::new).collect(Collectors.toList()), pagedUsers.getTotalElements(),
                pagedUsers.getTotalPages());
    }

    @Transactional
    public User removeUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        });

        userRepository.delete(user);

        return user;
    }

    public List<Bill> getUserBills(UUID userId) {
        return billRepository.getAllUserBills(userId);
    }

    public User updateUser(EditUserRequest request, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        });

        if(request.getUsername() != null && !request.getUsername().equals(user.getUsername()))
            user.setUsername(request.getUsername());

        if(request.getPassword() != null &&!passwordEncoder.matches(PasswordValidator.decodePassword(request.getPassword()), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(PasswordValidator.decodeAndValidatePassword(request.getPassword())));
        }

        if(request.getRoleName() != null) {
            UserRole role = this.userRoleService.getUserRoleByName(request.getRoleName());

            if(!user.getRole().getName().equals(role.getName()))
                user.setRole(role);
        }

        if(request.getWalletType() != null && !request.getWalletType().equals(user.getWallet())) {
            List<Bill> bills = billRepository.getAllUserBills(userId);

            for (Bill bill : bills) {
                CurrencySingletonCourse baseCourse = this.currencySingleton.findCourse(user.getWallet());
                CurrencySingletonCourse needCourse = this.currencySingleton.findCourse(request.getWalletType());

                Double amount = Double.parseDouble("%d.%d".formatted(bill.getBalance().getAmount(), bill.getBalance().getCents())) / baseCourse.getCourse() * needCourse.getCourse();

                String formattedValue = "%.2f".formatted(amount);

                if(!formattedValue.equals("0")) {
                    bill.getBalance().setAmount(Integer.parseInt(formattedValue.split("\\.")[0]));
                    bill.getBalance().setCents(Integer.parseInt(formattedValue.split("\\.")[1]));

                    billRepository.save(bill);
                }
            }

            user.setWallet(request.getWalletType());
        }

        if(request.getPlannedIncome() != null && request.getPlannedIncome() != user.getPlannedIncome())
            user.setPlannedIncome(request.getPlannedIncome());

        if(request.getNotificationsEnable() != null && request.getNotificationsEnable() != user.isNotificationsEnable())
            user.setNotificationsEnable(request.getNotificationsEnable());

        if(request.getEmail() != null && (user.getEmail().getAddress() == null || !user.getEmail().getAddress().equals(request.getEmail()))) {
            Optional<User> emailValidationUser = this.userRepository.findByEmail(request.getEmail());

            if(emailValidationUser.isPresent())
                throw new ServiceException("User with given email already exist", HttpStatus.BAD_REQUEST);

            UserEmail email = user.getEmail();
            email.setAddress(request.getEmail());
            email.setActivated(false);

            user.setEmail(email);
        }

        if(request.getTouchId() != null && !request.getTouchId().equals(user.isTouchId()))
            user.setTouchId(request.getTouchId());

        if(request.getFaceId() != null && !request.getFaceId().equals(user.isFaceId()))
            user.setFaceId(request.getFaceId());

        userRepository.save(user);

        return user;
    }

    public List<Category> getUserCategories(UUID userId) {
        return categoryRepository.getAllUserCategories(userId);
    }

    public List<SberCard> getUserSberCards(UUID userId) {
        return sberCardRepository.findByIntegrationUserId(userId);
    }

    public List<TinkoffCard> getUserTinkoffCards(UUID userId) {
        return tinkoffCardRepository.findByIntegrationUserId(userId);
    }

    public List<TochkaCard> getUserTochkaCards(UUID userId) {
        return tochkaCardRepository.findByIntegrationUserId(userId);
    }

    public List<LoyaltyCard> getUserLoyaltyCards(UUID userId) {
        return loyaltyCardRepository.getAllUserCards(userId);
    }

    public List<Activity> getUserActivityByPeriod(UUID userId, Instant startTime, Instant endTime) {
        return activityRepository.getUserActivityByPeriod(userId, Timestamp.from(startTime), Timestamp.from(endTime));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        });
    }

    public Subscription getUserSubscription(UUID userId) {
        return subscriptionRepository.getSubscriptionByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Subscription not found", HttpStatus.NOT_FOUND);
        });
    }
}
