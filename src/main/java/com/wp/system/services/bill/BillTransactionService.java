package com.wp.system.services.bill;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.bill.BillErrorCode;
import com.wp.system.repository.bill.BillTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillTransactionService {
    @Autowired
    private BillTransactionRepository billTransactionRepository;

    public List<BillTransaction> getAllTransactionsByPeriod(Instant start,
                                                            Instant end,
                                                            Integer count,
                                                            UUID userId,
                                                            UUID billId) {
        if (userId == null && billId == null)
            throw new ServiceException(BillErrorCode.NOT_FOUND_PARAMS);

        List<BillTransaction> transactions = null;

        List<BillTransaction> parsedTransactions = new ArrayList<>();

        if (userId != null)
            transactions = this.getAllUserTransactions(userId);
        else
            transactions = this.getAllTransactionsByBillId(billId);

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

    public List<BillTransaction> getAllUserTransactions(UUID userId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(userId);

        return transactions;
    }

    public List<BillTransaction> getAllTransactionsByBillId(UUID billId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllBillTransactions(billId);

        return transactions;
    }

}
