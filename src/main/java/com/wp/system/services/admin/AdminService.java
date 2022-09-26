package com.wp.system.services.admin;

import com.wp.system.config.security.UserAuthDetails;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.dto.sber.SberTransactionDTO;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.dto.tochka.TochkaTransactionDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.activity.Activity;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.tochka.TochkaTransaction;
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
import com.wp.system.repository.sber.SberTransactionRepository;
import com.wp.system.repository.subscription.SubscriptionRepository;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.repository.tinkoff.TinkoffTransactionRepository;
import com.wp.system.repository.tochkaa.TochkaCardRepository;
import com.wp.system.repository.tochkaa.TochkaTransactionRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.admin.ExtendSubscriptionRequest;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private TinkoffTransactionRepository tinkoffTransactionRepository;

    @Autowired
    private SberTransactionRepository sberTransactionRepository;

    @Autowired
    private TochkaTransactionRepository tochkaTransactionRepository;

    public User resetPin(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        });

        user.setPinCode(null);

        return user;
    }

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

                bill.setBalance(bill.getBalance().divide(BigDecimal.valueOf(baseCourse.getCourse()), 2, RoundingMode.CEILING).multiply(BigDecimal.valueOf(needCourse.getCourse())));
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

    public PagingResponse<TinkoffTransactionDTO> getUserTinkoffTransactions(UUID userId, int page, int pageSize) {
        Page<TinkoffTransaction> pagedBill = tinkoffTransactionRepository.findByCardIntegrationUserId(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(pagedBill.getContent().stream().map(TinkoffTransactionDTO::new).collect(Collectors.toList()),
                pagedBill.getTotalElements(), pagedBill.getTotalPages());
    }

    public PagingResponse<SberTransactionDTO> getUserSberTransactions(UUID userId, int page, int pageSize) {
        Page<SberTransaction> pagedBill = sberTransactionRepository.findByCardIntegrationUserId(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(pagedBill.getContent().stream().map(SberTransactionDTO::new).collect(Collectors.toList()),
                pagedBill.getTotalElements(), pagedBill.getTotalPages());
    }

    public PagingResponse<TochkaTransactionDTO> getUserTochkaTransactions(UUID userId, int page, int pageSize) {
        Page<TochkaTransaction> pagedBill = tochkaTransactionRepository.findByCardIntegrationUserId(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(pagedBill.getContent().stream().map(TochkaTransactionDTO::new).collect(Collectors.toList()),
                pagedBill.getTotalElements(), pagedBill.getTotalPages());
    }

    public PagingResponse<BillTransactionDTO> getUserBillTransactions(UUID userId, int page, int pageSize) {
        Page<BillTransaction> pagedBill = billTransactionRepository.getAllUserTransactions(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(pagedBill.getContent().stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                pagedBill.getTotalElements(), pagedBill.getTotalPages());
    }

    public Subscription resetSubscription(UUID userId) {
        Subscription subscription = subscriptionRepository.getSubscriptionByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Subscription not found", HttpStatus.NOT_FOUND);
        });

        subscription.setActive(false);
        subscription.setEndDate(null);
        subscription.setStartDate(null);

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription updateSubscription(ExtendSubscriptionRequest request, UUID userId) {
        Subscription subscription = subscriptionRepository.getSubscriptionByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Subscription not found", HttpStatus.NOT_FOUND);
        });

        subscription.setActive(true);
        subscription.setStartDate(Instant.now());
        subscription.setEndDate(Instant.now().plus(request.getDays(), ChronoUnit.DAYS));

        subscriptionRepository.save(subscription);

        return subscription;
    }
}
