package com.wp.system.repository.bill;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillRepository extends CrudRepository<Bill, UUID> {
    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.id = ?1")
    List<Bill> getAllUserBills(UUID userId);
}
