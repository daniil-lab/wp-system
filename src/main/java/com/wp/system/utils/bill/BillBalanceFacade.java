package com.wp.system.utils.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;

import java.time.Instant;

public class BillBalanceFacade {
    private BillBalanceLogger logger;

    private BillTransactionManager transactionManager;

    private Category category;

    private Bill bill;

    private User user;

    public BillBalanceFacade(BillBalanceLogger logger, BillTransactionManager transactionManager, Category category, Bill bill, User user) {
        this.logger = logger;
        this.transactionManager = transactionManager;
        this.category = category;
        this.bill = bill;
        this.user = user;
    }

    public BillTransaction deposit(int amount,
                                   int cents,
                                   String description,
                                   Instant time) {
        BillTransaction transaction = this.transactionManager.createTransaction(BillBalanceAction.DEPOSIT,
                amount,
                cents,
                bill,
                category,
                description,
                user,
                time);

        this.logger.createBillLog(BillBalanceAction.DEPOSIT,
                amount,
                cents,
                bill,
                category);

        this.bill.getBalance().deposit(amount, cents);

        return transaction;
    }

    public BillTransaction withdraw(int amount,
                         int cents,
                         String description,
                          Instant time) {
        BillTransaction transaction = this.transactionManager.createTransaction(BillBalanceAction.WITHDRAW,
                amount,
                cents,
                bill,
                category,
                description,
                user,
                time);

        this.logger.createBillLog(BillBalanceAction.WITHDRAW,
                amount,
                cents,
                bill,
                category);

        this.bill.getBalance().withdraw(amount, cents);

        return transaction;
    }
}
