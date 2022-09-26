package com.wp.system.utils.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillBalanceFacadeFactory {
    @Autowired
    private BillBalanceLogger logger;

    @Autowired
    private BillTransactionManager transactionManager;

    @Autowired
    private CategoryRepository categoryRepository;

    public BillBalanceFacadeFactory() {};

    public BillBalanceFacade getFacade(Category category, Bill bill, User user) {
        return new BillBalanceFacade(logger, transactionManager, category, bill, user, categoryRepository);
    }
}
