package com.itacademy.jd2.ai.b2b.web.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ProductDTO {

    private Integer id;
    
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String name;
    
    private String description;
    
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 150)
    private String externalLink;

    private Boolean visible;
    
    @NotNull
    private Integer position;
    
    @NotNull
    @Min(0)
    @Digits(integer = 10, fraction = 2) // валидация на 2 знака после запятой
    private Double price;
    
    @Min (0)
    private Integer quantityStock;

    private Integer brandId;

    private String brandName;

    private Integer categoryId;

    private String categoryName;
    
    private Set<Integer> imageIds;

    private Date created;

    private Date updated;
    
    
    private Integer version;

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(final String externalLink) {
        this.externalLink = externalLink;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(final Boolean visible) {
        this.visible = visible;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(final Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(final Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(final String brandName) {
        this.brandName = brandName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public Set<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(final Set<Integer> imageIds) {
        this.imageIds = imageIds;
    }


}
