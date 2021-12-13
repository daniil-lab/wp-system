package com.wp.system.other.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.repository.bill.BillLogRepository;
import com.wp.system.repository.bill.BillTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillTransactionManager {
    @Autowired
    private BillTransactionRepository billTransactionRepository;

    public BillTransactionManager() {}

    public BillTransaction createTransaction(BillBalanceAction action,
                                             int amount,
                                             int cents,
                                             Bill bill,
                                             Category category,
                                             String description) {
        BillTransaction transaction = new BillTransaction(action,
                amount,
                cents,
                bill,
                category,
                description);

        this.billTransactionRepository.save(transaction);

        return transaction;
    }
}
