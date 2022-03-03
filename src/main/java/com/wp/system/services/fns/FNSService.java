package com.wp.system.services.fns;

import com.wp.system.utils.fns.FNSAuthProvider;
import com.wp.system.utils.fns.FNSMessageData;
import com.wp.system.utils.fns.FNSTicketInformation;
import org.springframework.stereotype.Service;

@Service
public class FNSService {

    public String getTicketInformation(Integer sum,
                                       String date,
                                       String fn,
                                       int operationType,
                                       String fiscalDocumentId,
                                       String fiscalSign,
                                       boolean rawData) {
        return new FNSTicketInformation(new FNSAuthProvider(
                "MZXCnMtsrWdEkobAYyDhj4bTnQfXj5RFsVDqKNpNOqlWcjuJVaGjtIRuZQzKfDn25y0UXyutQB7hpPsimgkzQH9VU8YGpFGJBVlEtn1aJi3yxBpTkN2zaRxAw74hoXad"
        ).auth()).getTicketInformation(
                sum,
                date,
                fn,
                operationType,
                fiscalDocumentId,
                fiscalSign,
                rawData
        );
    }

    public String getMessageById(String messageId) {
        return new FNSMessageData(new FNSAuthProvider(
                "MZXCnMtsrWdEkobAYyDhj4bTnQfXj5RFsVDqKNpNOqlWcjuJVaGjtIRuZQzKfDn25y0UXyutQB7hpPsimgkzQH9VU8YGpFGJBVlEtn1aJi3yxBpTkN2zaRxAw74hoXad"
        ).auth()).getMessageData(messageId);
    }
}
