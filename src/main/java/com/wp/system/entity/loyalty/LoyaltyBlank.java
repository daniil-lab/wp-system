package com.wp.system.entity.loyalty;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class LoyaltyBlank {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="image_id")
    private SystemImage image;

    @OneToMany(mappedBy="blank", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LoyaltyCard> cards;

    public LoyaltyBlank() {}

    public LoyaltyBlank(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemImage getImage() {
        return image;
    }

    public void setImage(SystemImage image) {
        this.image = image;
    }
}
