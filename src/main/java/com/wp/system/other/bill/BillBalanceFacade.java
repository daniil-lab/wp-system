package com.wp.system.other.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillBalance;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class BillBalanceFacade {
    private BillBalanceLogger logger;

    private BillTransactionManager transactionManager;

    private Category category;

    private Bill bill;

    public BillBalanceFacade(BillBalanceLogger logger, BillTransactionManager transactionManager, Category category, Bill bill) {
        this.logger = logger;
        this.transactionManager = transactionManager;
        this.category = category;
        this.bill = bill;
    }

    public void deposit(int amount,
                        int cents,
                        String description) {
        this.transactionManager.createTransaction(BillBalanceAction.DEPOSIT,
                amount,
                cents,
                bill,
                category,
                description);

        this.logger.createBillLog(BillBalanceAction.DEPOSIT,
                amount,
                cents,
                bill,
                category);

        this.bill.getBalance().deposit(amount, cents);
    }

    public void withdraw(int amount,
                         int cents,
                         String description) {
        this.transactionManager.createTransaction(BillBalanceAction.WITHDRAW,
                amount,
                cents,
                bill,
                category,
                description);

        this.logger.createBillLog(BillBalanceAction.WITHDRAW,
                amount,
                cents,
                bill,
                category);

        this.bill.getBalance().withdraw(amount, cents);
    }
}
