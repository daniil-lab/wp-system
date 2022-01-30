package com.wp.system.services.bill;

import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillTransactionRepository;
import com.wp.system.response.PagingResponse;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

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

        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();;
        CriteriaQuery<BillTransaction> cr = cb.createQuery(BillTransaction.class);
        Root<BillTransaction> root = cr.from(BillTransaction.class);

        Predicate[] predicates = new Predicate[2];

        predicates[0] = cb.between(root.get("createAt"), Timestamp.from(start), Timestamp.from(end));

        if(userId != null)
            predicates[1] = cb.equal(root.join("user").get("id"), userId);
        else predicates[1] = cb.equal(root.join("bill").get("id"), billId);

        List<BillTransaction> transactions = entityManager
                .createQuery(cr.select(root).where(predicates))
                .setFirstResult(page * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        List<BillTransaction> transactionsFull = entityManager
                .createQuery(cr.select(root).where(predicates))
                .getResultList();

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).filter((item) -> {
            if(categoryId != null) {
                if(item.getCategory() == null)
                    return false;

                if(item.getCategory().getId().equals(categoryId))
                    return true;
                else
                    return false;
            }

            return true;
        }).collect(Collectors.toList()),
            transactionsFull.size());
    }

    public PagingResponse<BillTransactionDTO> getAllCategoryTransactions(UUID categoryId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllCategoryTransactions(categoryId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.findAll().size());
    }

    public PagingResponse<BillTransactionDTO> getAllUserTransactions(UUID userId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(userId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.findAll().size());
    }

    public PagingResponse<BillTransactionDTO> getAllTransactionsByBillId(UUID billId, int page, int pageSize) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllBillTransactions(billId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.stream().map(BillTransactionDTO::new).collect(Collectors.toList()),
                this.billTransactionRepository.findAll().size());
    }

}
