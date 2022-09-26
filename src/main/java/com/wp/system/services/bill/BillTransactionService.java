package com.wp.system.services.bill;

import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillRepository;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.bill.UpdateBillTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.bill.TransactionType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillTransactionService {
    @Autowired
    private BillTransactionRepository billTransactionRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthHelper authHelper;

    @Transactional
    public BillTransaction updateBillTransaction(UpdateBillTransactionRequest request, UUID id) {
        BillTransaction transaction = billTransactionRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Transaction not found", HttpStatus.NOT_FOUND);
        });

        User user = authHelper.getUserFromAuthCredentials();

        if(!transaction.getBill().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);

        if(request.getAction() != null)
            transaction.setAction(request.getAction());

        if(request.getBillId() != null)
            transaction.setBill(billRepository.findById(request.getBillId()).orElseThrow(() -> {
                throw new ServiceException("Bill not found", HttpStatus.NOT_FOUND);
            }));

        if(request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
                throw new ServiceException("Category not found", HttpStatus.NOT_FOUND);
            });

            if(TransactionType.WITHDRAW == request.getAction()) {
                if(!category.getForSpend())
                    throw new ServiceException("Указанная категория не для расходов", HttpStatus.BAD_REQUEST);
            }

            if(TransactionType.DEPOSIT == request.getAction()) {
                if(!category.getForEarn())
                    throw new ServiceException("Указанная категория не для доходов", HttpStatus.BAD_REQUEST);
            }

            if(request.getAction() == TransactionType.WITHDRAW) {
                category.setCategorySpend(category.getCategorySpend().add(request.getAmount()));

                if(category.getCategoryLimit().compareTo(new BigDecimal("0")) != 0) {
                    category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100.0")));
                }

//                if(category != null && category.getCategoryLimit().compareTo(new BigDecimal("0")) == 0) {
//                    category.setCategorySpend(category.getCategorySpend().add(request.getAmount()));
//                    category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100.0")));
//                    categoryRepository.save(category);
//                }
            } else {
                category.setCategoryEarn(category.getCategoryEarn().add(request.getAmount()));
            }

            transaction.setCategory(category);
        }

        if(request.getCurrency() != null)
            transaction.setCurrency(request.getCurrency());

        if(request.getLatitude() != null)
            transaction.setLatitude(request.getLatitude());

        if(request.getLongitude() != null)
            transaction.setLongitude(request.getLongitude());

        if(request.getGeocodedPlace() != null)
            transaction.setGeocodedPlace(request.getGeocodedPlace());

        if(request.getDescription() != null)
            transaction.setDescription(request.getDescription());

        if(request.getAmount() != null) {
            transaction.setSum(request.getAmount());
        }

        billTransactionRepository.save(transaction);

        return transaction;
    }

    @Transactional
    public BillTransaction removeTransaction(UUID transactionId) {
        BillTransaction transaction = this.getBillTransactionById(transactionId);

        Bill bill = billRepository.findById(transaction.getBill().getId()).orElseThrow(() -> {
            throw new ServiceException("Счет не найден", HttpStatus.NOT_FOUND);
        });

        if(transaction.getAction() == TransactionType.DEPOSIT)
            bill.setBalance(bill.getBalance().subtract(transaction.getSum()));
        else
            bill.setBalance(bill.getBalance().add(transaction.getSum()));

        billRepository.save(bill);

        transaction.setBill(null);
        transaction.setCategory(null);
        transaction.setUser(null);

        billTransactionRepository.delete(transaction);

        return transaction;
    }

    public BillTransaction getBillTransactionById(UUID transactionId) {
        Optional<BillTransaction> foundTransaction = billTransactionRepository.findById(transactionId);

        if(foundTransaction.isEmpty())
            throw new ServiceException("Bill Transaction not found", HttpStatus.NOT_FOUND);

        User user = authHelper.getUserFromAuthCredentials();

        if(!foundTransaction.get().getBill().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);

        return foundTransaction.get();
    }

    public PagingResponse<BillTransactionDTO> getAllTransactionsByPeriod(Instant start,
                                                            Instant end,
                                                            int page,
                                                            int pageSize,
                                                            UUID userId,
                                                            UUID billId,
                                                            UUID categoryId) {
        if (userId == null && billId == null)
            throw new ServiceException("Pass to Request Params userId or billId", HttpStatus.BAD_REQUEST);

        User user = authHelper.getUserFromAuthCredentials();

        if(userId != null) {
            if(!userId.equals(user.getId()))
                throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);
        }

        if(billId != null) {
            Bill bill = billRepository.findById(billId).orElseThrow(() -> {
                throw new ServiceException("Bill not found", HttpStatus.NOT_FOUND);
            });

            if(!bill.getUser().getId().equals(user.getId()))
                throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);
        }

        Page<BillTransaction> transactions = userId != null ? billTransactionRepository.getAllUserTransactionsByPeriod(userId, Timestamp.from(start),
                Timestamp.from(end), PageRequest.of(page, pageSize)) :
                billTransactionRepository.getBillTransactionsByPeriod(billId, Timestamp.from(start),
                        Timestamp.from(end), PageRequest.of(page, pageSize));

        return new PagingResponse<BillTransactionDTO>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                transactions.getTotalElements(), transactions.getTotalPages());
    }

    public PagingResponse<BillTransactionDTO> getAllCategoryTransactions(UUID categoryId, int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ServiceException("Category not found", HttpStatus.NOT_FOUND);
        });

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);

        List<BillTransaction> transactions = this.billTransactionRepository.getAllCategoryTransactions(categoryId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.findAll().size());
    }

    public PagingResponse<BillTransactionDTO> getAllUserTransactions(int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        Page<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(user.getId(), PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                transactions.getTotalElements(), transactions.getTotalPages());
    }

    public PagingResponse<BillTransactionDTO> getAllTransactionsByBillId(UUID billId, int page, int pageSize, Instant startDate, Instant endDate) {
        User user = authHelper.getUserFromAuthCredentials();

        Bill bill = billRepository.findById(billId).orElseThrow(() -> {
            throw new ServiceException("Bill not found", HttpStatus.NOT_FOUND);
        });

        if(!bill.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transaction", HttpStatus.FORBIDDEN);

        Page<BillTransaction> transactions = this.billTransactionRepository.findByBillIdAndCreateAtBetween(billId, startDate, endDate, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.getContent().stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                transactions.getTotalElements(), transactions.getTotalPages());
    }

}
