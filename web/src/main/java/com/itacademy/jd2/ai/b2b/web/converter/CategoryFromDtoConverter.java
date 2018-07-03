package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.dto.CategoryDTO;

@Component
public class CategoryFromDtoConverter implements Function<CategoryDTO, ICategory> {

    @Autowired
    private IImageService imageService;
    @Autowired
    private ICategoryService productService;

    @Override
    public ICategory apply(final CategoryDTO dto) {
        final ICategory entity = productService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPosition(dto.getPosition());

        final IImage image = imageService.createEntity();
        image.setId(dto.getImageId());
        entity.setImage(image);

        return entity;
    }
}
