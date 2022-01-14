package com.wp.system.services.bill;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.bill.BillTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillTransactionService {
    @Autowired
    private BillTransactionRepository billTransactionRepository;

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

    public List<BillTransaction> getAllTransactionsByPeriod(Instant start,
                                                            Instant end,
                                                            Integer count,
                                                            UUID userId,
                                                            UUID billId,
                                                            UUID categoryId) {
        if (userId == null && billId == null)
            throw new ServiceException("Pass to Request Params userId or billId", HttpStatus.BAD_REQUEST);

        List<BillTransaction> transactions = null;

        List<BillTransaction> parsedTransactions = new ArrayList<>();

        if (userId != null)
            transactions = this.getAllUserTransactions(userId);
        else if(billId != null)
            transactions = this.getAllTransactionsByBillId(billId);
        else if (categoryId != null)
            transactions = this.getAllCategoryTransactions(categoryId);

        if(transactions == null)
            return new ArrayList<>();

        for (BillTransaction transaction : transactions) {
            if(transaction.getCreateAt() == null)
                continue;

            Instant transactionDate = Instant.parse(transaction.getCreateAt());

            if((!transactionDate.isBefore( start )) && (transactionDate.isBefore(end)))
                parsedTransactions.add(transaction);

            if(count != -1)
                if(parsedTransactions.size() >= count)
                    break;
        }

        return parsedTransactions;
    }

    public List<BillTransaction> getAllCategoryTransactions(UUID categoryId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllCategoryTransactions(categoryId);

        return transactions;
    }

    public List<BillTransaction> getAllUserTransactions(UUID userId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(userId);

        return transactions;
    }

    public List<BillTransaction> getAllTransactionsByBillId(UUID billId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllBillTransactions(billId);

        return transactions;
    }

}
