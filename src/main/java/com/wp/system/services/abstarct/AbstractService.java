package com.wp.system.services.abstarct;

import com.wp.system.dto.AbstractIntegrationDTO;
import com.wp.system.dto.AbstractTransactionDTO;
import com.wp.system.dto.bill.AbstractBillDTO;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.entity.user.User;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.repository.sber.SberIntegrationRepository;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.repository.tochkaa.TochkaIntegrationRepository;
import com.wp.system.response.PagingResponse;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AbstractService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TinkoffIntegrationRepository tinkoffIntegrationRepository;

    @Autowired
    private SberIntegrationRepository sberIntegrationRepository;

    @Autowired
    private TochkaIntegrationRepository tochkaIntegrationRepository;

    public List<AbstractIntegrationDTO> getIntegrations() {
        User user = authHelper.getUserFromAuthCredentials();

        List<AbstractIntegrationDTO> integrations = new ArrayList<>();

        tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId()).ifPresent((i) -> integrations.add(new AbstractIntegrationDTO(i)));
        sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).ifPresent((i) -> integrations.add(new AbstractIntegrationDTO(i)));

        return integrations;
    }

    public List<AbstractBillDTO> getBills() {
        User user = authHelper.getUserFromAuthCredentials();
        TinkoffIntegration tinkoffIntegration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId()).orElse(null);
        SberIntegration sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).orElse(null);

        String stringQuery = "(SELECT CAST(id as varchar), balance, hidden, name, 'SYSTEM' FROM bill WHERE user_id = :user_id)";

        if(tinkoffIntegration != null)
            stringQuery += " union (SELECT CAST(id as varchar), balance, hidden, name, 'TINKOFF' FROM tinkoff_card WHERE integration_id = :tinkoff_integration_id)";


        if(sberIntegration != null)
            stringQuery += " union (SELECT CAST(id as varchar), balance, hidden, name, 'SBER' FROM sber_card WHERE integration_id = :sber_integration_id)";

        Query query = entityManager.createNativeQuery(
                stringQuery
        );

        query.setParameter("user_id", user.getId());

        if(tinkoffIntegration != null)
            query.setParameter("tinkoff_integration_id", tinkoffIntegration.getId());

        if(sberIntegration != null)
            query.setParameter("sber_integration_id", sberIntegration.getId());

        List<Object[]> results = query.getResultList();

        return results.stream().map(item -> {
            AbstractBillDTO dto = new AbstractBillDTO();

            dto.setId(UUID.fromString((String) item[0]));
            dto.setBalance((BigDecimal) item[1]);
            dto.setHidden((Boolean) item[2]);
            dto.setName((String) item[3]);
            dto.setBillType((String) item[4]);

            return dto;
        }).collect(Collectors.toList());
    }



    public PagingResponse<AbstractTransactionDTO> getAllTransactions(Instant startDate, Instant endDate, TransactionType transactionType, String billType, UUID billId, int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        String queryString = null;
        Query query = null;

        if(billType == null) {
            if(transactionType == null) {
                queryString = "((select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND user_id = :userId) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select tc.id from tinkoff_card as tc where tc.integration_id = (select ti.id from tinkoff_integration as ti where ti.user_id = :userId))) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))) order by create_at desc limit :limit offset :offset";
            } else if(transactionType == TransactionType.DEPOSIT) {
                queryString = "((select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND user_id = :userId AND action = 1) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE transaction_type = 1 AND date BETWEEN :startDate AND :endDate AND card_id in (select tc.id from tinkoff_card as tc where tc.integration_id = (select ti.id from tinkoff_integration as ti where ti.user_id = :userId))) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE transaction_type = 1 AND date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))) order by create_at desc limit :limit offset :offset";
            } else if(transactionType == TransactionType.WITHDRAW) {
                queryString = "((select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND user_id = :userId AND action = 0) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE transaction_type = 0 AND date BETWEEN :startDate AND :endDate AND card_id in (select tc.id from tinkoff_card as tc where tc.integration_id = (select ti.id from tinkoff_integration as ti where ti.user_id = :userId))) union (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE transaction_type = 0 AND date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))) order by create_at desc limit :limit offset :offset";
            }
        } else {
            if(billType.equals("SYSTEM")) {
                if(transactionType == null) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND bill_id = :cardId order by create_at desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.DEPOSIT) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND bill_id = :cardId AND action = 1 order by create_at desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.WITHDRAW) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND bill_id = :cardId AND action = 0 order by create_at desc limit :limit offset :offset";
                }
            } else if(billType.equals("TINKOFF")) {
                if(transactionType == null) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.DEPOSIT) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE transaction_type = 1 AND date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.WITHDRAW) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE transaction_type = 0 AND date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                }
            } else if(billType.equals("SBER")) {
                if(transactionType == null) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.DEPOSIT) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE transaction_type = 1 AND date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                } else if(transactionType == TransactionType.WITHDRAW) {
                    queryString = "select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE transaction_type = 0 AND date BETWEEN :startDate AND :endDate AND card_id = :cardId order by date desc limit :limit offset :offset";
                }
            }
        }

        System.out.println(queryString);

        query = entityManager.createNativeQuery(queryString);
        // TOCHKA =                 union
        //                (select CAST(id as varchar), CAST(category_id as varchar), amount as sum, transaction_type, date, currency, description, 'TOCHKA' as transactionType, (SELECT tkc.card_number FROM tochka_card as tkc WHERE tkc.id = tochka_transaction.card_id) as billName from tochka_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))
        if(queryString.contains("userId"))
            query.setParameter("userId", user.getId());

        if(queryString.contains("cardId"))
            query.setParameter("cardId", billId);

        query.setParameter("startDate", Timestamp.from(startDate));
        query.setParameter("endDate", Timestamp.from(endDate));
        query.setParameter("limit", pageSize);
        query.setParameter("offset", page * pageSize);

        List<Object[]> results = query.getResultList();

        List<AbstractTransactionDTO> transactions = new ArrayList<>();
        results.forEach(item -> {
            AbstractTransactionDTO dto = new AbstractTransactionDTO();

            dto.setId(UUID.fromString((String)item[0]));

            if(item[1] != null) {
                categoryRepository.findById(UUID.fromString((String)item[1])).ifPresent(c -> {
                    dto.setCategory(new CategoryDTO(c));
                });
            }

            dto.setSum((BigDecimal) item[2]);

            if((Integer) item[3] == 0) {
                dto.setTransactionType(TransactionType.WITHDRAW.name());
            } else {
                dto.setTransactionType(TransactionType.DEPOSIT.name());
            }

            dto.setDate(((Timestamp) item[4]).toInstant());
            dto.setCurrency(WalletType.values()[(Integer) item[5]]);
            dto.setDescription((String) item[6]);
            dto.setType((String) item[7]);
            dto.setBillName((String) item[8]);
            dto.setBillId((String) item[9]);

            transactions.add(dto);
        });

        return new PagingResponse<>(transactions, 0, 0);
    }
}
