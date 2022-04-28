package com.wp.system.dto.loyalty;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.user.User;

import java.util.UUID;

public class LoyaltyCardDTO {

    private UUID id;

    private UserDTO user;

    private LoyaltyBlankDTO blank;

    private String data;

    private LoyaltyCardCustomImageDTO customImage;

    public LoyaltyCardDTO() {}

    public LoyaltyCardDTO(LoyaltyCard card) {
        this.id = card.getId();
        this.user = card.getUser() == null ? null : new UserDTO(card.getUser());
        this.blank = card.getBlank() == null ? null : new LoyaltyBlankDTO(card.getBlank());
        this.data = card.getData();
        this.customImage = card.getCustomImage() == null ? null : new LoyaltyCardCustomImageDTO(card.getCustomImage());
    }

    public LoyaltyCardCustomImageDTO getCustomImage() {
        return customImage;
    }

    public void setCustomImage(LoyaltyCardCustomImageDTO customImage) {
        this.customImage = customImage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LoyaltyBlankDTO getBlank() {
        return blank;
    }

    public void setBlank(LoyaltyBlankDTO blank) {
        this.blank = blank;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
