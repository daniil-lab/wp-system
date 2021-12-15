package com.wp.system.services.bill;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.repository.bill.BillTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BillTransactionService {
    @Autowired
    private BillTransactionRepository billTransactionRepository;

    public List<BillTransaction> getAllUserTransactions(UUID userId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllUserTransactions(userId);

        return transactions;
    }

    public List<BillTransaction> getAllTransactionsByBillId(UUID billId) {
        List<BillTransaction> transactions = this.billTransactionRepository.getAllBillTransactions(billId);

        return transactions;
    }

}
