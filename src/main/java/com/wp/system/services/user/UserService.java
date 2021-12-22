package com.wp.system.services.user;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.system.SystemErrorCode;
import com.wp.system.exception.user.UserErrorCode;
import com.wp.system.other.CSVConverter;
import com.wp.system.other.CurrencySingleton;
import com.wp.system.other.CurrencySingletonCourse;
import com.wp.system.other.bill.BillBalanceAction;
import com.wp.system.other.email.local.LocalMailSender;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.user.*;
import com.wp.system.services.bill.BillService;
import com.wp.system.services.bill.BillTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            throw new ServiceException(SystemErrorCode.INTERNAL_ERROR);
        }
    }

    public User addTokenToUser(AddUserDeviceTokenRequest request) {
        User user = this.getUserById(request.getUserId());

        if(user.getDeviceTokens().contains(request.getToken()))
            throw new ServiceException(UserErrorCode.DEVICE_TOKEN_ALREADY_EXIST);

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

    public User createUser(CreateUserRequest request) {
        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        Optional<User> foundUser = this.userRepository.findByUsername(request.getUsername());

        if(foundUser.isPresent())
            throw new ServiceException(UserErrorCode.ALREADY_EXIST);

        UserRole role = null;

        if(request.getRoleName() == null)
            role = this.userRoleService.getAutoApplyRole();
        else
            role = this.userRoleService.getUserRoleByName(request.getRoleName());

        User user = new User(request.getUsername(), passwordEncoder.encode(new String(passwordBytes)));

        user.setRole(role);
        user.setWallet(request.getWalletType());
        user.setEmail(request.getEmail());
        user.setUserType(request.getType());

        userRepository.save(user);

        return user;
    }

    public User getUserByUsername(String username) {
        Optional<User> foundUser = this.userRepository.findByUsername(username);

        if(foundUser.isEmpty())
            throw new ServiceException(UserErrorCode.NOT_FOUND);

        return foundUser.get();
    }

    public List<User> getAllUsers() {
        Iterable<User> foundUsers = this.userRepository.findAll();
        List<User> users = new ArrayList<>();

        foundUsers.forEach(users::add);

        return users;
    }

    public User getUserById(UUID id) {
        Optional<User> foundUser = this.userRepository.findById(id);

        if(foundUser.isEmpty())
            throw new ServiceException(UserErrorCode.NOT_FOUND);

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
                        Integer.parseInt(formattedValue.split(",")[0]),
                        Integer.parseInt(formattedValue.split(",")[1]));
                }
            }

            user.setWallet(request.getWalletType());
        }

        if(request.getPlannedIncome() != null && request.getPlannedIncome() != user.getPlannedIncome())
            user.setPlannedIncome(request.getPlannedIncome());

        if(request.getNotificationsEnable() != null && request.getNotificationsEnable() != user.isNotificationsEnable())
            user.setNotificationsEnable(request.getNotificationsEnable());

        if(request.getEmail() != null && !user.getEmail().equals(request.getEmail()))
            user.setEmail(request.getEmail());

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
