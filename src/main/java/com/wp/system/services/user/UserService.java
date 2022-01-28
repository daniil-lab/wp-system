package com.wp.system.services.user;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.BaseCategory;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserEmail;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.subscription.Subscription;
import com.wp.system.exception.ServiceException;
import com.wp.system.other.CSVConverter;
import com.wp.system.other.CurrencySingleton;
import com.wp.system.other.CurrencySingletonCourse;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.user.*;
import com.wp.system.response.user.UserPageResponse;
import com.wp.system.services.bill.BillService;
import com.wp.system.services.bill.BillTransactionService;
import com.wp.system.services.category.BaseCategoryService;
import com.wp.system.services.category.CategoryService;
import com.wp.system.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private BillService billService;

    @Autowired
    private CurrencySingleton currencySingleton;

    @Autowired
    private BillTransactionService billTransactionService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BaseCategoryService baseCategoryService;

    @Autowired
    private CategoryService categoryService;

    public User activateUserEmail(UUID userId) {
        User user = this.getUserById(userId);

        UserEmail email = user.getEmail();

        email.setActivated(true);

        user.setEmail(email);

        userRepository.save(user);

        return user;
    }

    public File exportCSVData(ExportDataRequest request) {
        User user = this.getUserById(request.getUserId());

        List<BillTransaction> transactions = this.billTransactionService.getAllTransactionsByPeriod(
                request.getStart(),
                request.getEnd(),
                -1,
                user.getId(),
                null,
                null
        );

        File csvFile = new File("data" + Instant.now() + user.getId() + ".csv");

        List<String[]> dataLines = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").withZone(ZoneId.systemDefault());

        dataLines.add(new String[] {
                "Дата",
                "Место",
                "Действие",
                "Сумма",
                "Валюта",
                "Счет"
        });

        for(BillTransaction transaction : transactions)
            dataLines.add(new String[] {
                    formatter.format(Instant.parse(transaction.getCreateAt())),
                    (transaction.getGeocodedPlace() != null ?
                            transaction.getGeocodedPlace() : "Отсутствует"),
                    transaction.getAction().getPaymentType(),
                    transaction.getSum().toString(),
                    transaction.getCurrency().getWalletName(),
                    transaction.getBill().getName()
            });

        try {
            PrintWriter writer = new PrintWriter(csvFile);

            for (String[] data : dataLines) {
                String result = CSVConverter.convertToCSV(data);
                writer.write(result);
            }

            writer.close();

            return csvFile;
        } catch (FileNotFoundException e) {
            throw new ServiceException("Error on ejecting data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User addTokenToUser(AddUserDeviceTokenRequest request) {
        User user = this.getUserById(request.getUserId());

        if(user.getDeviceTokens().contains(request.getToken()))
            throw new ServiceException("Device token already exist", HttpStatus.BAD_REQUEST);

        user.addDeviceToken(request.getToken());

        userRepository.save(user);

        return user;
    }

    public User removeDeviceTokenFromUser(UUID userId, String token) {
        User user = this.getUserById(userId);

        user.removeDeviceToken(token);

        userRepository.save(user);

        return user;
    }

    public User cleanUserData(CleanUserRequest request) {
        User user = this.getUserById(request.getUserId());

        List<BillTransaction> transactions = this.billTransactionService.getAllTransactionsByPeriod(
                request.getStart(),
                request.getEnd(),
                -1,
                request.getUserId(),
                null,
                null
        );

        for (BillTransaction transaction : transactions)
            this.billTransactionService.removeTransaction(transaction.getId());

        return user;
    }

    public User setUserPincode(SetUserPincodeRequest request) {
        User user = this.getUserById(request.getUserId());

        user.setPinCode(request.getCode());

        userRepository.save(user);

        return user;
    }

    public User removeUserPincode(UUID userId) {
        User user = this.getUserById(userId);

        user.setPinCode(null);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public User createUser(CreateUserRequest request) {
        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        Optional<User> foundUser = this.userRepository.findByUsername(request.getUsername());

        if(foundUser.isPresent())
            throw new ServiceException("User with given phone already exist", HttpStatus.BAD_REQUEST);

        Optional<User> emailValidationUser = this.userRepository.findByEmail(request.getEmail());

        if(emailValidationUser.isPresent())
            throw new ServiceException("User with given email already exist", HttpStatus.BAD_REQUEST);

        UserRole role = null;

        if(request.getRoleName() == null)
            role = this.userRoleService.getAutoApplyRole();
        else
            role = this.userRoleService.getUserRoleByName(request.getRoleName());

        User user = new User(request.getUsername(), passwordEncoder.encode(new String(passwordBytes)));

        UserEmail email = new UserEmail();
        email.setAddress(request.getEmail());

        user.setRole(role);
        user.setWallet(request.getWalletType());
        user.setEmail(email);
        user.setUserType(request.getType());
        user.setSubscription(new Subscription());

        userRepository.save(user);

        List<BaseCategory> baseCategories = baseCategoryService.getAllBaseCategories();

        for (BaseCategory baseCategory : baseCategories)
            categoryService.createCategory(new CreateCategoryRequest(
                    baseCategory,
                    user.getId()
            ));

        return user;
    }

    public User getUserByEmail(String email) {
        Optional<User> foundUser = this.userRepository.findByEmail(email);

        if(foundUser.isEmpty())
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);

        return foundUser.get();
    }

    public User getUserByUsername(String username) {
        Optional<User> foundUser = this.userRepository.findByUsername(username);

        if(foundUser.isEmpty())
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);

        return foundUser.get();
    }

    public UserPageResponse getUsersByPage(int page, int pageSize) {
        return new UserPageResponse(userRepository.findAll(PageRequest.of(page, pageSize)).stream().map(UserDTO::new).collect(Collectors.toList()), getAllUsers().size());
    }

    public List<User> findUser(String phone) {
        List<User> users = getAllUsers();

        return users.stream().filter((user) -> {
            return user.getUsername().contains(phone);
        }).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        Iterable<User> foundUsers = this.userRepository.findAll();
        List<User> users = new ArrayList<>();

        foundUsers.forEach(users::add);

        System.out.println(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());

        return users;
    }

    public User getUserById(UUID id) {
        Optional<User> foundUser = this.userRepository.findById(id);

        if(foundUser.isEmpty())
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);

        return foundUser.get();
    }

    @Transactional
    public User updateUser(EditUserRequest request, UUID userId) {
        User user = this.getUserById(userId);

        if(request.getUsername() != null && !request.getUsername().equals(user.getUsername()))
            user.setUsername(request.getUsername());

        if(request.getPassword() != null &&!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());
            user.setPassword(passwordEncoder.encode(new String(passwordBytes)));
        }

        if(request.getRoleName() != null) {
            UserRole role = this.userRoleService.getUserRoleByName(request.getRoleName());

            if(!user.getRole().getName().equals(role.getName()))
                user.setRole(role);
        }

        if(request.getWalletType() != null && !request.getWalletType().equals(user.getWallet())) {
            List<Bill> bills = this.billService.getUserBills(user.getId());

            for (Bill bill : bills) {
                CurrencySingletonCourse baseCourse = this.currencySingleton.findCourse(user.getWallet());
                CurrencySingletonCourse needCourse = this.currencySingleton.findCourse(request.getWalletType());

                Double amount = Double.parseDouble("%d.%d".formatted(bill.getBalance().getAmount(), bill.getBalance().getCents())) / baseCourse.getCourse() * needCourse.getCourse();

                String formattedValue = "%.2f".formatted(amount);

                if(!formattedValue.equals("0")) {
                    this.billService.updateBillBalance(bill.getId(),
                        Integer.parseInt(formattedValue.split("\\.")[0]),
                        Integer.parseInt(formattedValue.split("\\.")[1]));
                }
            }

            user.setWallet(request.getWalletType());
        }

        if(request.getPlannedIncome() != null && request.getPlannedIncome() != user.getPlannedIncome())
            user.setPlannedIncome(request.getPlannedIncome());

        if(request.getNotificationsEnable() != null && request.getNotificationsEnable() != user.isNotificationsEnable())
            user.setNotificationsEnable(request.getNotificationsEnable());

        if(request.getEmail() != null && !user.getEmail().getAddress().equals(request.getEmail())) {
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

    @Transactional
    public User removeUser(UUID id) {
        User user = this.getUserById(id);

        userRepository.delete(user);

        return user;
    }
}
