package com.wp.system.services.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillBalance;
import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillLogRepository;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.Geocoder;
import com.wp.system.utils.bill.BillBalanceFacade;
import com.wp.system.utils.bill.BillBalanceFacadeFactory;
import com.wp.system.repository.bill.BillBalanceRepository;
import com.wp.system.repository.bill.BillRepository;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.request.bill.CreateBillRequest;
import com.wp.system.request.bill.DepositBillRequest;
import com.wp.system.request.bill.EditBillRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.services.category.CategoryService;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.bill.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillBalanceRepository billBalanceRepository;

    @Autowired
    private BillTransactionRepository billTransactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BillLogRepository billLogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BillBalanceFacadeFactory billBalanceFacadeFactory;

    @Autowired
    private AuthHelper authHelper;

    public Bill updateBill(EditBillRequest request, UUID billId) {
        Bill bill = this.getBillById(billId);
        User user = authHelper.getUserFromAuthCredentials();

        if(!bill.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your bill", HttpStatus.FORBIDDEN);

        if(request.getName() != null && !bill.getName().equals(request.getName()))
            bill.setName(request.getName());

        if(request.getBalance() != null)
            bill.setBalance(request.getBalance());

        if(request.getHidden() != null)
            bill.setHidden(request.getHidden());

        this.billRepository.save(bill);

        return bill;
    }

    @Transactional
    public Bill createBill(CreateBillRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        Bill bill = new Bill(request.getName(), user);

        bill.setBalance(request.getBalance());

        this.billRepository.save(bill);

        return bill;
    }

    public Bill getBillById(UUID id) {
        Optional<Bill> bill = this.billRepository.findById(id);

        if(bill.isEmpty())
            throw new ServiceException("Bill not found.", HttpStatus.NOT_FOUND);

        User user = authHelper.getUserFromAuthCredentials();

        if(!bill.get().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your bill", HttpStatus.FORBIDDEN);

        return bill.get();
    }

    public List<Bill> getUserBills() {
        User user = authHelper.getUserFromAuthCredentials();

        List<Bill> bills = this.billRepository.getAllUserBills(user.getId());

        return bills;
    }

    @Transactional
    public BillTransaction withdrawBill(WithdrawBillRequest request, UUID billId) {
        User user = authHelper.getUserFromAuthCredentials();

        Bill bill = this.getBillById(billId);

        Category category = null;

        if(request.getCategoryId() != null)
            category = this.categoryService.getCategoryById(request.getCategoryId());

        if(category != null) {
            if(!category.getForSpend()) {
                throw new ServiceException("Указанная категория не может содержать расходов", HttpStatus.BAD_REQUEST);
            }
        }

        BillTransaction transaction = new BillTransaction();

        transaction.setAction(TransactionType.WITHDRAW);
        transaction.setSum(request.getAmount());
        transaction.setCategory(category);
        transaction.setLongitude(request.getLon());
        transaction.setLatitude(request.getLat());
        transaction.setUser(user);
        transaction.setCurrency(user.getWallet());
        transaction.setDescription(request.getDescription());
        transaction.setBill(bill);

        if(request.getPlaceName() != null)
            transaction.setGeocodedPlace(request.getPlaceName());
        else
            if(request.getLon() != null && request.getLat() != null) {
                String place = Geocoder.getPlaceByCoords(request.getLon(), request.getLat());

                transaction.setGeocodedPlace(place);
                transaction.setLatitude(request.getLat());
                transaction.setLongitude(request.getLon());
            }

        billTransactionRepository.save(transaction);

        bill.setBalance(bill.getBalance().subtract(request.getAmount()));

        this.billRepository.save(bill);

        BillLog log = new BillLog();

        log.setBill(bill);
        log.setCategory(category);
        log.setAction(TransactionType.WITHDRAW);
        log.setSum(request.getAmount());

        billLogRepository.save(log);

        if(category != null) {
            Long count = billTransactionRepository.countByCategoryIdAndActionAndCreateAtBetween(category.getId(), TransactionType.WITHDRAW, Instant.now(), Instant.now().minus(31 * 3, ChronoUnit.DAYS).atOffset(ZoneOffset.UTC).toInstant());
            Long sum = billTransactionRepository.getSumByCategory(category.getId(), TransactionType.WITHDRAW, Instant.now(), Instant.now().minus(31 * 3, ChronoUnit.DAYS).atOffset(ZoneOffset.UTC).toInstant());

            System.out.println(count);
            System.out.println(sum);
            System.out.println(Instant.now().minus(31 * 3, ChronoUnit.DAYS).atOffset(ZoneOffset.UTC).toInstant());
            System.out.println(Instant.now().atOffset(ZoneOffset.UTC).toInstant());

            if(sum != null && count != null && count != 0) {
                category.setSpendStatistic(BigDecimal.valueOf(sum.intValue() / count.intValue()));

                categoryRepository.save(category);
            }
        }

        return transaction;
    }

    @Transactional
    public BillTransaction depositBill(DepositBillRequest request, UUID billId) {
        User user = authHelper.getUserFromAuthCredentials();

        Bill bill = this.getBillById(billId);

        Category category = null;

        if(request.getCategoryId() != null)
            category = this.categoryService.getCategoryById(request.getCategoryId());

        if(category != null) {
            if(!category.getForEarn()) {
                throw new ServiceException("Указанная категория не может содержать пополнений", HttpStatus.BAD_REQUEST);
            }
        }

        BillTransaction transaction = new BillTransaction();

        transaction.setAction(TransactionType.DEPOSIT);
        transaction.setSum(request.getAmount());
        transaction.setCategory(category);
        transaction.setUser(user);
        transaction.setCurrency(user.getWallet());
        transaction.setDescription(request.getDescription());
        transaction.setBill(bill);

        BillLog log = new BillLog();

        log.setBill(bill);
        log.setCategory(category);
        log.setAction(TransactionType.DEPOSIT);
        log.setSum(request.getAmount());

        billTransactionRepository.save(transaction);

        billLogRepository.save(log);

        bill.setBalance(bill.getBalance().add(request.getAmount()));

        this.billRepository.save(bill);

        if(category != null) {
            Long count = billTransactionRepository.countByCategoryIdAndActionAndCreateAtBetween(category.getId(), TransactionType.DEPOSIT, Instant.now(), Instant.now().minus(31 * 3, ChronoUnit.DAYS).atOffset(ZoneOffset.UTC).toInstant());
            Long sum = billTransactionRepository.getSumByCategory(category.getId(), TransactionType.DEPOSIT, Instant.now(), Instant.now().minus(31 * 3, ChronoUnit.DAYS).atOffset(ZoneOffset.UTC).toInstant());

            System.out.println(count);
            System.out.println(sum);

            if((sum != null && count != null && count != 0)) {
                category.setEarnStatistic(BigDecimal.valueOf(sum.intValue() / count.intValue()));

                categoryRepository.save(category);
            }
        }

        return transaction;
    }

    @Transactional
    public Bill removeBill(UUID billId) {
        Bill bill = this.getBillById(billId);

        bill.setUser(null);

        billLogRepository.findByBillId(bill.getId()).forEach(b -> {
            b.setBill(null);
            b.setCategory(null);

            billLogRepository.delete(b);
        });

        bill.setLogs(new ArrayList<>());

        Query query = entityManager.createNativeQuery("DELETE FROM bill_transaction WHERE bill_id = :billId");
        query.setParameter("billId", bill.getId());

        query.executeUpdate();

        this.billRepository.delete(bill);

        return bill;
    }
}
