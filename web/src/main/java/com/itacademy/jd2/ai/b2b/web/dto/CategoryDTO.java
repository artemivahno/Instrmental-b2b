package com.itacademy.jd2.ai.b2b.web.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CategoryDTO {

    private Integer id;

    @Size(min = 1, max = 50)
    private String name;

    private String description;

    @Min (0)
    private Integer position;

    private Integer imageId;

    private String imageName;

    private Date created;

    private Date updated;

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(final Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

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
}
