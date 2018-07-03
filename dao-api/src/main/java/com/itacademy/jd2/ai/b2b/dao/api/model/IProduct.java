package com.itacademy.jd2.ai.b2b.dao.api.model;

import java.util.Set;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IProduct extends IBaseEntity {

    Integer DEFAULT_VERSION = 1;

    String getName();

    String getDescription();

    String getExternalLink();

    Boolean getVisible();

    Integer getPosition();

    Double getPrice();

    Integer getQuantityStock();
    
    Integer getVersion();
    
    Set<IImage> getImages();
    
    void setName(String name);

    void setDescription(String description);

    void setExternalLink(String externalLink);

    void setVisible(Boolean visible);

    void setPosition(Integer position);

    void setPrice(Double newPrice);

    void setQuantityStock(Integer quantityStock);

    ICategory getCategory();

    void setCategory(ICategory category);

    IBrand getBrand();

    void setBrand(IBrand brand);
    
    void setImages(Set<IImage> images);
    
    void setVersion(Integer version);
    
    

}
