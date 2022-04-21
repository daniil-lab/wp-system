package com.wp.system.services.loyalty;

import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.loyalty.LoyaltyCardRepository;
import com.wp.system.request.loyalty.CreateLoyaltyCardRequest;
import com.wp.system.request.loyalty.UpdateLoyaltyCardRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AuthHelper authHelper;

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
            throw new ServiceException("Loyalty Card not found", HttpStatus.NOT_FOUND);

        User user = authHelper.getUserFromAuthCredentials();

        if(!user.getId().equals(card.get().getUser().getId()))
            throw new ServiceException("It`s not your card", HttpStatus.FORBIDDEN);

        return card.get();
    }

    public List<LoyaltyCard> getAllUserLoyaltyCards() {
        User user = authHelper.getUserFromAuthCredentials();

        List<LoyaltyCard> cards = loyaltyCardRepository.getAllUserCards(user.getId());

        return cards;
    }

    @Transactional
    public LoyaltyCard removeLoyaltyCard(UUID id) {
        User user = authHelper.getUserFromAuthCredentials();

        LoyaltyCard card = this.getLoyaltyCardById(id);

        if(!card.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your card", HttpStatus.FORBIDDEN);

        loyaltyCardRepository.delete(card);

        return card;
    }
}
