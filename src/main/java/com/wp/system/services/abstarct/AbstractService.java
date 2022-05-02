package com.wp.system.services.abstarct;

import com.wp.system.entity.user.User;
import com.wp.system.response.PagingResponse;
import com.wp.system.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class AbstractService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthHelper authHelper;

    public PagingResponse<Object> getAllTransactions(Instant startDate, Instant endDate, int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        Query query = entityManager.createNativeQuery("((select id, category_id, sum, action, create_at, currency, description, 'SYSTEM' as transaction_type from bill_transaction WHERE create_at BETWEEN timestamp :startDate AND timestamp :endDate AND user_id = :userId)\n" +
                "union\n" +
                "(select id, category_id, CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType from tinkoff_transaction WHERE date BETWEEN timestamp :startDate AND timestamp :endDate AND card_id in (select tc.id from tinkoff_card as tc where tc.integration_id = (select ti.id from tinkoff_integration as ti where ti.user_id = :userId)))\n" +
                "union\n" +
                "(select id, category_id, CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'SBER' as transactionType from sber_transaction WHERE date BETWEEN timestamp :startDate AND timestamp :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))\n" +
                "union\n" +
                "(select id, category_id, CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'TOCHKA' as transactionType from tochka_transaction WHERE date BETWEEN timestamp :startDate AND timestamp :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))\n" +
                ") order by create_at desc limit :limit offset :offset");

        query.setParameter("userId", user.getId());
        query.setParameter("startDate", Timestamp.from(startDate));
        query.setParameter("endDate", Timestamp.from(endDate));
        query.setParameter("limit", pageSize);
        query.setParameter("offset", page * pageSize);

        List<Object> results = query.getResultList();

        results.forEach(item -> {
            System.out.println(((Map<String, Object>) item).get("id"));
        });

        return null;
    }
}
