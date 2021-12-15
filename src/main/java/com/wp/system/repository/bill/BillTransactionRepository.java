package com.wp.system.repository.bill;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillTransactionRepository extends CrudRepository<BillTransaction, UUID> {
    @Query("SELECT t FROM BillTransaction t JOIN t.bill b JOIN b.user u WHERE u.id = ?1")
    List<BillTransaction> getAllUserTransactions(UUID userId);

    @Query("SELECT t FROM BillTransaction t JOIN t.bill b WHERE b.id = ?1")
    List<BillTransaction> getAllBillTransactions(UUID billId);
}
