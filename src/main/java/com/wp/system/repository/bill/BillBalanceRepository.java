package com.wp.system.repository.bill;

import com.wp.system.entity.bill.BillBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillBalanceRepository extends CrudRepository<BillBalance, UUID> {
}
