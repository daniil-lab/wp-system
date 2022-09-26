package com.wp.system.repository.bill;

import com.wp.system.entity.bill.BillLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillLogRepository extends CrudRepository<BillLog, UUID> {
    List<BillLog> findByBillId(UUID billId);
}
