package com.wp.system.services.abstarct;

import com.wp.system.dto.AbstractTransactionDTO;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.BankTransactionType;
import com.wp.system.entity.category.Category;
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
import com.wp.system.utils.bill.BillBalanceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

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

    public PagingResponse<AbstractTransactionDTO> getAllTransactions(Instant startDate, Instant endDate, int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        Optional<TinkoffIntegration> tinkoffIntegration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());
        Optional<SberIntegration> sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(user.getId());
        Optional<TochkaIntegration> tochkaIntegration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId());

        Query query = entityManager.createNativeQuery("""
                ((select CAST(id as varchar), CAST(category_id as varchar), sum, action, create_at, currency, description, 'SYSTEM' as transaction_type, (SELECT b.name FROM bill as b WHERE b.id = bill_transaction.bill_id) as billName, CAST(bill_transaction.bill_id as varchar) as billId from bill_transaction WHERE create_at BETWEEN :startDate AND :endDate AND user_id = :userId)
                union
                (select CAST(id as varchar), CAST(category_id as varchar), CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'TINKOFF' as transactionType, (SELECT tc.name FROM tinkoff_card as tc WHERE tc.id = tinkoff_transaction.card_id) as billName, CAST(card_id as varchar) as billId from tinkoff_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select tc.id from tinkoff_card as tc where tc.integration_id = (select ti.id from tinkoff_integration as ti where ti.user_id = :userId)))
                union
                (select CAST(id as varchar), CAST(category_id as varchar), CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'SBER' as transactionType, (SELECT sc.name FROM sber_card as sc WHERE sc.id = sber_transaction.card_id) as billName, CAST(card_id as varchar) as billId from sber_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))
                ) order by create_at desc limit :limit offset :offset""");

        // TOCHKA =                 union
        //                (select CAST(id as varchar), CAST(category_id as varchar), CAST(CONCAT(CAST(amount as varchar),'.' ,CAST(cents as varchar)) as float) as sum, transaction_type, date, currency, description, 'TOCHKA' as transactionType, (SELECT tkc.card_number FROM tochka_card as tkc WHERE tkc.id = tochka_transaction.card_id) as billName from tochka_transaction WHERE date BETWEEN :startDate AND :endDate AND card_id in (select sc.id from sber_card as sc where sc.integration_id = (select si.id from sber_integration as si where si.user_id = :userId)))

        query.setParameter("userId", user.getId());
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

            dto.setSum((Double) item[2]);

            if(item[7].equals("SYSTEM")) {
                if((Integer) item[3] == 0) {
                    dto.setTransactionType(BillBalanceAction.WITHDRAW.name());
                } else {
                    dto.setTransactionType(BillBalanceAction.DEPOSIT.name());
                }
            } else {
                dto.setTransactionType(BankTransactionType.values()[(Integer) item[3]].name());
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
