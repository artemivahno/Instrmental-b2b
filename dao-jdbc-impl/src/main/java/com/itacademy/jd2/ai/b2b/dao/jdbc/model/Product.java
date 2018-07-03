package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import java.util.Set;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

public class Product extends BaseEntity implements IProduct {

    private String name;
    private String description;
    private String externalLink;
    private Boolean visible;
    private Integer position;
    private Double price;
    private Integer quantityStock;
    private ICategory category;
    private IBrand brand;
    private Integer version;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String getExternalLink() {
        return externalLink;
    }

    @Override
    public void setExternalLink(final String externalLink) {
        this.externalLink = externalLink;
    }

    @Override
    public Boolean getVisible() {
        return visible;
    }

    @Override
    public void setVisible(final Boolean visible) {
        this.visible = visible;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Integer position) {
        this.position = position;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(final Double price) {
        this.price = price;
    }

    @Override
    public Integer getQuantityStock() {
        return quantityStock;
    }

    @Override
    public void setQuantityStock(final Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    @Override
    public ICategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(final ICategory category) {
        this.category = category;
    }

    @Override
    public IBrand getBrand() {
        return brand;
    }

    @Override
    public void setBrand(final IBrand brand) {
        this.brand = brand;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Set<IImage> getImages() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setImages(Set<IImage> images) {
        // TODO Auto-generated method stub
        
    }


}
