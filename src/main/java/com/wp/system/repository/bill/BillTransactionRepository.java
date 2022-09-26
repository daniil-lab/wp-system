package com.wp.system.repository.bill;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.utils.bill.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BillTransactionRepository extends JpaRepository<BillTransaction, UUID> {
    @Query("SELECT t FROM BillTransaction t JOIN t.bill b JOIN b.user u WHERE u.id = ?1")
    Page<BillTransaction> getAllUserTransactions(UUID userId, Pageable pageable);

    @Query("SELECT t FROM BillTransaction t JOIN t.bill b JOIN b.user u WHERE u.id = ?1")
    List<BillTransaction> getAllUserTransactions(UUID userId);

    @Query("SELECT COALESCE(SUM(t.sum), 0) FROM BillTransaction t WHERE t.category.id = ?1 AND t.action = ?2 AND t.createAt BETWEEN ?3 AND ?4")
    Long getSumByCategory(UUID categoryId, TransactionType transactionType, Instant firstDate, Instant secondDate);

    Long countByCategoryIdAndActionAndCreateAtBetween(UUID categoryId, TransactionType transactionType, Instant firstDate, Instant secondDate);

    Page<BillTransaction> findByCategoryIdAndCreateAtBetween(UUID categoryId, Instant startDate, Instant endDate, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM bill_transaction WHERE user_id = ?1 AND create_at BETWEEN ?2 AND ?3")
    Page<BillTransaction> getAllUserTransactionsByPeriod(UUID userId, Timestamp startTime, Timestamp endTime, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM bill_transaction WHERE user_id = ?1 AND create_at BETWEEN ?2 AND ?3")
    List<BillTransaction> getAllUserTransactionsByPeriod(UUID userId, Timestamp startTime, Timestamp endTime);

    @Query(nativeQuery = true, value = "SELECT * FROM bill_transaction WHERE bill_id = ?1 AND create_at BETWEEN ?2 AND ?3")
    Page<BillTransaction> getBillTransactionsByPeriod(UUID bullId, Timestamp startTime, Timestamp endTime, Pageable pageable);

    Page<BillTransaction> findByBillIdAndCreateAtBetween(UUID billId, Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT t FROM BillTransaction t JOIN t.bill b WHERE b.id = ?1")
    List<BillTransaction> getAllBillTransactions(UUID billId);

    @Query("SELECT t FROM BillTransaction t JOIN t.category c WHERE c.id = ?1")
    List<BillTransaction> getAllCategoryTransactions(UUID categoryId, Pageable pageable);

    List<BillTransaction> findByCategoryIdAndCreateAtGreaterThanEqual(UUID categoryId, Instant date);
}
