package com.itacademy.jd2.ai.b2b.dao.orm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

@Entity
public class Product extends BaseEntity implements IProduct {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String externalLink;

    @Column
    private Boolean visible;

    @Column
    private Integer position;

    @Column
    private Double price;

    @Column
    private Integer quantityStock;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
    private ICategory category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Brand.class)
    private IBrand brand;

    @JoinTable(name = "product_2_image", joinColumns = {
            @JoinColumn(name = "product_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "image_id") })
    @ManyToMany(targetEntity = Image.class, fetch = FetchType.LAZY)
    //@OrderBy("title ASC")
    private Set<IImage> images = new HashSet<>();

    /*@JoinTable(name = "product_2_attribute", joinColumns = {
            @JoinColumn(name = "product_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "attribute_id") })
    @ManyToMany(targetEntity = Attribute.class, fetch = FetchType.LAZY)

    @OrderBy("title ASC")
    private Set<IAttribute> attribute = new HashSet<>();*/

    @Column
    @Version
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
        return images;
    }

    @Override
    public void setImages(Set<IImage> images) {
        this.images = images;
    }

}
