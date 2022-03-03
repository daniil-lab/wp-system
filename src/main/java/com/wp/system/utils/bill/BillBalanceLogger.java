package com.wp.system.utils.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.category.Category;
import com.wp.system.repository.bill.BillLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillBalanceLogger {
    @Autowired
    private BillLogRepository billLogRepository;

    public BillBalanceLogger() {}

    public BillLog createBillLog(BillBalanceAction action, int amount, int cents, Bill bill, Category category) {
        BillLog log = new BillLog(action, amount, cents, bill, category);

        this.billLogRepository.save(log);

        return log;
    }
}
