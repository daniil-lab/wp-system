package com.wp.system.dto.category;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

public class CategoryDTO {
    @Schema(description = "ID категории")
    private UUID id;

    @Schema(description = "Название категории")
    private String name;

    @Schema(description = "Цвет категории")
    private CategoryDTOColor color;

    @Schema(description = "Иконка категории")
    private SystemImageDTO icon;

    @Schema(description = "Описание категории")
    private String description;

    @Schema(description = "Лимит категории")
    private int categoryLimit;

    @Schema(description = "Пользователь, к которому прикреплена категория")
    private UserDTO user;

    private Boolean forEarn;

    private Boolean forSpend;

    private Double percentsFromLimit;

    private Double categorySpend;

    private Double categoryEarn;

    public CategoryDTO(Category category) {
        if(category == null)
            return;

        this.percentsFromLimit = category.getPercentsFromLimit();
        this.forEarn = category.getForEarn();
        this.forSpend = category.getForSpend();
        this.categorySpend = category.getCategorySpend();
        this.categoryEarn = category.getCategoryEarn();
        this.id = category.getId();
        this.name = category.getName();
        this.color = new CategoryDTOColor(category.getColor());
        this.icon = category.getIcon() != null ? new SystemImageDTO(category.getIcon()) : null;
        this.description = category.getDescription();
        this.user = new UserDTO(category.getUser());
        this.categoryLimit = category.getCategoryLimit();
    }

    public Double getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(Double categorySpend) {
        this.categorySpend = categorySpend;
    }

    public Double getCategoryEarn() {
        return categoryEarn;
    }

    public void setCategoryEarn(Double categoryEarn) {
        this.categoryEarn = categoryEarn;
    }

    public Boolean getForEarn() {
        return forEarn;
    }

    public void setForEarn(Boolean forEarn) {
        this.forEarn = forEarn;
    }

    public Boolean getForSpend() {
        return forSpend;
    }

    public void setForSpend(Boolean forSpend) {
        this.forSpend = forSpend;
    }

    public Double getPercentsFromLimit() {
        return percentsFromLimit;
    }

    public void setPercentsFromLimit(Double percentsFromLimit) {
        this.percentsFromLimit = percentsFromLimit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDTOColor getColor() {
        return color;
    }

    public void setColor(CategoryDTOColor color) {
        this.color = color;
    }

    public SystemImageDTO getIcon() {
        return icon;
    }

    public void setIcon(SystemImageDTO icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }
}
