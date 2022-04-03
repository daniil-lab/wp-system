package com.wp.system.services.bill;

import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillRepository;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.bill.UpdateBillTransactionRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.category.CategoryService;
import com.wp.system.utils.Geocoder;
import com.wp.system.utils.bill.BillBalanceFacade;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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

    @Transactional
    public BillTransaction updateBillTransaction(UpdateBillTransactionRequest request, UUID id) {
        BillTransaction transaction = billTransactionRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Transaction not found", HttpStatus.NOT_FOUND);
        });

        if(request.getAction() != null)
            transaction.setAction(request.getAction());

        if(request.getBillId() != null)
            transaction.setBill(billRepository.findById(request.getBillId()).orElseThrow(() -> {
                throw new ServiceException("Bill not found", HttpStatus.NOT_FOUND);
            }));

        if(request.getBillId() != null)
            transaction.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
                throw new ServiceException("Category not found", HttpStatus.NOT_FOUND);
            }));

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
            if(request.getCents() == null)
                throw new ServiceException("If you pass amount, pass and cents", HttpStatus.BAD_REQUEST);

            transaction.setSum(Double.parseDouble(request.getAmount() + "." + request.getCents()));
        }

        billTransactionRepository.save(transaction);

        return transaction;
    }

    @Transactional
    public BillTransaction removeTransaction(UUID transactionId) {
        BillTransaction transaction = this.getBillTransactionById(transactionId);

        billTransactionRepository.delete(transaction);

        return transaction;
    }

    public BillTransaction getBillTransactionById(UUID transactionId) {
        Optional<BillTransaction> foundTransaction = billTransactionRepository.findById(transactionId);

        if(foundTransaction.isEmpty())
            throw new ServiceException("Bill Transaction not found", HttpStatus.NOT_FOUND);

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

        Page<BillTransaction> transactions = userId != null ? billTransactionRepository.getAllUserTransactionsByPeriod(userId, Timestamp.from(start),
                Timestamp.from(end), PageRequest.of(page, pageSize)) :
                billTransactionRepository.getBillTransactionsByPeriod(billId, Timestamp.from(start),
                        Timestamp.from(end), PageRequest.of(page, pageSize));

        return new PagingResponse<BillTransactionDTO>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                transactions.getTotalElements(), transactions.getTotalPages());
    }

    public PagingResponse<BillTransactionDTO> getAllCategoryTransactions(UUID categoryId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllCategoryTransactions(categoryId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.findAll().size());
    }

    public PagingResponse<BillTransactionDTO> getAllUserTransactions(UUID userId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.getAllUserTransactions(userId).size());
    }

    public PagingResponse<BillTransactionDTO> getAllTransactionsByBillId(UUID billId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllBillTransactions(billId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.getAllBillTransactions(billId).size());
    }

}
