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
import java.math.BigDecimal;
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
    private BigDecimal categoryLimit;

    @Schema(description = "Пользователь, к которому прикреплена категория")
    private UserDTO user;

    private Boolean forEarn;

    private Boolean forSpend;

    private BigDecimal percentsFromLimit;

    private BigDecimal categorySpend;

    private BigDecimal categoryEarn;

    private BigDecimal spendStatistic;

    private BigDecimal earnStatistic;

    private Boolean favorite;

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
        this.favorite = category.getFavorite();
        this.spendStatistic = category.getSpendStatistic();
        this.earnStatistic = category.getEarnStatistic();
    }

    public BigDecimal getSpendStatistic() {
        return spendStatistic;
    }

    public void setSpendStatistic(BigDecimal spendStatistic) {
        this.spendStatistic = spendStatistic;
    }

    public BigDecimal getEarnStatistic() {
        return earnStatistic;
    }

    public void setEarnStatistic(BigDecimal earnStatistic) {
        this.earnStatistic = earnStatistic;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public BigDecimal getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(BigDecimal categorySpend) {
        this.categorySpend = categorySpend;
    }

    public BigDecimal getCategoryEarn() {
        return categoryEarn;
    }

    public void setCategoryEarn(BigDecimal categoryEarn) {
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

    public BigDecimal getPercentsFromLimit() {
        return percentsFromLimit;
    }

    public void setPercentsFromLimit(BigDecimal percentsFromLimit) {
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

    public BigDecimal getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(BigDecimal categoryLimit) {
        this.categoryLimit = categoryLimit;
    }
}
