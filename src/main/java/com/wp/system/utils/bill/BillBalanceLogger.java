package com.wp.system.utils.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.category.Category;
import com.wp.system.repository.bill.BillLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BillBalanceLogger {
    @Autowired
    private BillLogRepository billLogRepository;

    public BillBalanceLogger() {}

    public BillLog createBillLog(TransactionType action, BigDecimal amount, Bill bill, Category category) {
        BillLog log = new BillLog(action, amount, bill, category);

        this.billLogRepository.save(log);

        return log;
    }
}
