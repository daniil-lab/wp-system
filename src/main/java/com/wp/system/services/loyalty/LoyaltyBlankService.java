package com.wp.system.services.loyalty;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.loyalty.LoyaltyBlankRepository;
import com.wp.system.request.loyalty.CreateLoyaltyBlankRequest;
import com.wp.system.request.loyalty.UpdateLoyaltyBlankRequest;
import com.wp.system.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoyaltyBlankService {

    @Autowired
    private LoyaltyBlankRepository loyaltyBlankRepository;

    @Autowired
    private ImageService imageService;

    public LoyaltyBlank updateLoyaltyBlank(UpdateLoyaltyBlankRequest request, UUID id) {
        LoyaltyBlank blank = this.getLoyaltyBlankById(id);

        if(request.getDescription() != null && !request.getDescription().equals(blank.getDescription()))
            blank.setDescription(request.getDescription());

        if(request.getName() != null && !request.getName().equals(blank.getName()))
            blank.setName(request.getName());

        if(request.getImageId() != null) {
            SystemImage image = this.imageService.getImageById(request.getImageId());

            blank.setImage(image);
        }

        loyaltyBlankRepository.save(blank);

        return blank;
    }

    public LoyaltyBlank createLoyaltyBlank(CreateLoyaltyBlankRequest request) {
        LoyaltyBlank loyaltyBlank = new LoyaltyBlank(request.getName(), request.getDescription());

        if(request.getImageId() != null) {
            SystemImage image = this.imageService.getImageById(request.getImageId());

            loyaltyBlank.setImage(image);
        }

        loyaltyBlankRepository.save(loyaltyBlank);

        return loyaltyBlank;
    }

    public List<LoyaltyBlank> getAllLoyaltyBlanks() {
        Iterable<LoyaltyBlank> blanks = loyaltyBlankRepository.findAll();
        List<LoyaltyBlank> loyaltyBlanks = new ArrayList<>();

        blanks.forEach(loyaltyBlanks::add);

        return loyaltyBlanks;
    }

    public LoyaltyBlank getLoyaltyBlankById(UUID id) {
        Optional<LoyaltyBlank> blank = loyaltyBlankRepository.findById(id);

        if(blank.isEmpty())
            throw new ServiceException("Loyalty Blank not found", HttpStatus.NOT_FOUND);

        return blank.get();
    }

    @Transactional
    public LoyaltyBlank removeLoyaltyBlank(UUID id) {
        LoyaltyBlank blank = this.getLoyaltyBlankById(id);

        loyaltyBlankRepository.delete(blank);

        return blank;
    }
}
