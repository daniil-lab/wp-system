package com.wp.system.services.loyalty;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.loyalty.LoyaltyCardImage;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.loyalty.LoyaltyCardImageRepository;
import com.wp.system.repository.loyalty.LoyaltyCardRepository;
import com.wp.system.request.loyalty.CreateLoyaltyCardRequest;
import com.wp.system.request.loyalty.UpdateLoyaltyCardRequest;
import com.wp.system.request.loyalty.UploadCustomLoyaltyCardImageRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
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

    @Autowired
    private LoyaltyCardImageRepository loyaltyCardImageRepository;

    public LoyaltyCard removeCustomImage(UUID cardId) {
        LoyaltyCard card = loyaltyCardRepository.findById(cardId).orElseThrow(() -> {
            throw new ServiceException("Card not found", HttpStatus.NOT_FOUND);
        });

        User user = authHelper.getUserFromAuthCredentials();

        if(!card.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your card", HttpStatus.FORBIDDEN);

        try {
            Files.deleteIfExists(Path.of(card.getCustomImage().getPath()));
        } catch (Exception e) {
            throw new ServiceException("Error on remove file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        loyaltyCardImageRepository.delete(card.getCustomImage());

        card.setCustomImage(null);

        loyaltyCardRepository.save(card);

        return card;
    }

    public Tuple2<byte[], String> getImageContent(String path) {
        try {
            File image = new File(path);

            byte[] bytes = Files.readAllBytes(image.toPath());

            return Tuples.of(bytes, Files.probeContentType(image.toPath()));
        } catch(Exception e) {
            throw new ServiceException("Error on send image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public LoyaltyCard uploadCustomImage(UploadCustomLoyaltyCardImageRequest request, UUID cardId) {
        try {
            LoyaltyCard card = loyaltyCardRepository.findById(cardId).orElseThrow(() -> {
                throw new ServiceException("Card not found", HttpStatus.NOT_FOUND);
            });

            User user = authHelper.getUserFromAuthCredentials();

            if(!card.getUser().getId().equals(user.getId()))
                throw new ServiceException("It`s not your card", HttpStatus.FORBIDDEN);

            String fileName = Instant.now().toString() + "-" + UUID.randomUUID() + "." + request.getFile().getOriginalFilename().split("\\.")[1];

            String uploadDir = "loyaltyCardsImages/";

            FileUploadUtil.saveFile(uploadDir, fileName, request.getFile());

            LoyaltyCardImage image = new LoyaltyCardImage();

            image.setPath(uploadDir + fileName);
            image.setContentType(request.getFile().getContentType());

            loyaltyCardImageRepository.save(image);

            card.setCustomImage(image);

            loyaltyCardRepository.save(card);

            return card;
        } catch (Exception e) {
            throw new ServiceException("Upload error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

        card.setUser(null);
        card.setCustomImage(null);
        card.setBlank(null);

        loyaltyCardRepository.delete(card);

        return card;
    }
}
