package com.wp.system.services.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillBalance;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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

        if(request.getNewCents() != null) {
            bill.getBalance().setCents(request.getNewCents());
        }

        if(request.getNewAmount() != null)
            bill.getBalance().setAmount(request.getNewAmount());

        this.billRepository.save(bill);

        return bill;
    }

    @Transactional
    public Bill createBill(CreateBillRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        Bill bill = new Bill(request.getName(), user);
        BillBalance balance = new BillBalance(bill);

        bill.setBalance(balance);
        bill.getBalance().deposit(request.getBalance(), request.getCents());

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
        Bill bill = this.getBillById(billId);

        Category category = null;

        if(request.getCategoryId() != null)
            category = this.categoryService.getCategoryById(request.getCategoryId());

        if(category != null) {
            if(!category.getForSpend()) {
                throw new ServiceException("?????????????????? ?????????????????? ???? ?????????? ?????????????????? ????????????????", HttpStatus.BAD_REQUEST);
            }
        }

        BillBalanceFacade facade = billBalanceFacadeFactory.getFacade(category, bill, bill.getUser());

        BillTransaction transaction = facade.withdraw(request.getAmount(), request.getCents(), request.getDescription(), request.getTime());
        if(category != null && category.getCategoryLimit() != 0) {
            category.setCategorySpend(category.getCategorySpend() + request.getAmount());
            category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100.0);
            categoryRepository.save(category);
        }

        if(request.getPlaceName() != null)
            transaction.setGeocodedPlace(request.getPlaceName());
        else
            if(request.getLon() != null && request.getLat() != null) {
                String place = Geocoder.getPlaceByCoords(request.getLon(), request.getLat());

                transaction.setGeocodedPlace(place);
                transaction.setLatitude(request.getLat());
                transaction.setLongitude(request.getLon());
            }

        this.billTransactionRepository.save(transaction);

        this.billRepository.save(bill);

        return transaction;
    }

    @Transactional
    public Bill updateBillBalance(UUID billId, int amount, int cents) {
        Bill bill = this.getBillById(billId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!bill.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your bill", HttpStatus.FORBIDDEN);

        bill.getBalance().setAmount(amount);
        bill.getBalance().setCents(cents);
        bill.getBalance().deposit(0, 0);

        this.billRepository.save(bill);

        return bill;
    }

    @Transactional
    public BillTransaction depositBill(DepositBillRequest request, UUID billId) {
        Bill bill = this.getBillById(billId);

        Category category = null;

        if(request.getCategoryId() != null)
            category = this.categoryService.getCategoryById(request.getCategoryId());

        if(category != null) {
            if(!category.getForEarn()) {
                throw new ServiceException("?????????????????? ?????????????????? ???? ?????????? ?????????????????? ????????????????????", HttpStatus.BAD_REQUEST);
            }
        }

        BillBalanceFacade facade = billBalanceFacadeFactory.getFacade(category, bill, bill.getUser());

        BillTransaction transaction = facade.deposit(request.getAmount(), request.getCents(), request.getDescription(), request.getTime());

        this.billRepository.save(bill);

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
