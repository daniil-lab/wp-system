package com.wp.system.utils.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.repository.category.CategoryRepository;

import java.math.BigDecimal;
import java.time.Instant;

public class BillBalanceFacade {
    private BillBalanceLogger logger;

    private BillTransactionManager transactionManager;

    private Category category;

    private Bill bill;

    private User user;

    private CategoryRepository categoryRepository;

    public BillBalanceFacade(BillBalanceLogger logger, BillTransactionManager transactionManager, Category category, Bill bill, User user, CategoryRepository categoryRepository) {
        this.logger = logger;
        this.transactionManager = transactionManager;
        this.category = category;
        this.bill = bill;
        this.user = user;
        this.categoryRepository = categoryRepository;
    }

    public BillTransaction deposit(BigDecimal amount,
                                   String description,
                                   Instant time) {
        BillTransaction transaction = this.transactionManager.createTransaction(TransactionType.DEPOSIT,
                amount,
                bill,
                category,
                description,
                user,
                time);

        this.logger.createBillLog(TransactionType.DEPOSIT,
                amount,
                bill,
                category);

        this.bill.setBalance(bill.getBalance().add(amount));

        return transaction;
    }

    public BillTransaction withdraw(BigDecimal amount,
                                    String description,
                                    Instant time) {
        BillTransaction transaction = this.transactionManager.createTransaction(TransactionType.WITHDRAW,
                amount,
                bill,
                category,
                description,
                user,
                time);

        this.logger.createBillLog(TransactionType.WITHDRAW,
                amount,
                bill,
                category);

        this.bill.setBalance(bill.getBalance().subtract(amount));

        return transaction;
    }
}
