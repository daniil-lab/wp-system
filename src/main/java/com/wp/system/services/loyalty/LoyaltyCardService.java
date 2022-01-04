package com.wp.system.services.loyalty;

import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.loyalty.LoyaltyCardErrorCode;
import com.wp.system.repository.loyalty.LoyaltyCardRepository;
import com.wp.system.request.loyalty.CreateLoyaltyCardRequest;
import com.wp.system.request.loyalty.UpdateLoyaltyCardRequest;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoyaltyCardService {

    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoyaltyBlankService loyaltyBlankService;

    public LoyaltyCard createLoyaltyCard(CreateLoyaltyCardRequest request) {
        User user = this.userService.getUserById(request.getUserId());

        LoyaltyBlank blank = this.loyaltyBlankService.getLoyaltyBlankById(request.getBlankId());

        LoyaltyCard card = new LoyaltyCard(user, blank, request.getData());

        loyaltyCardRepository.save(card);

        return card;
    }

    public LoyaltyCard getLoyaltyCardById(UUID id) {
        Optional<LoyaltyCard> card = loyaltyCardRepository.findById(id);

        if(card.isEmpty())
            throw new ServiceException(LoyaltyCardErrorCode.NOT_FOUND);

        return card.get();
    }

    public List<LoyaltyCard> getAllLoyaltyCards() {
        Iterable<LoyaltyCard> cards = loyaltyCardRepository.findAll();

        List<LoyaltyCard> loyaltyCards = new ArrayList<>();

        cards.forEach(loyaltyCards::add);

        return loyaltyCards;
    }

    public List<LoyaltyCard> getAllUserLoyaltyCards(UUID userId) {
        List<LoyaltyCard> cards = loyaltyCardRepository.getAllUserCards(userId);

        return cards;
    }

    @Transactional
    public LoyaltyCard removeLoyaltyCard(UUID id) {
        LoyaltyCard card = this.getLoyaltyCardById(id);

        loyaltyCardRepository.delete(card);

        return card;
    }
}
