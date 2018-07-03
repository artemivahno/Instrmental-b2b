package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.web.dto.CategoryDTO;

@Component
public class CategoryToDtoConverter implements Function<ICategory, CategoryDTO> {

    @Override
    public CategoryDTO apply(final ICategory entity) {
        final CategoryDTO CategoryDto = new CategoryDTO();
        CategoryDto.setId(entity.getId());
        CategoryDto.setName(entity.getName());
        CategoryDto.setDescription(entity.getDescription());
        CategoryDto.setPosition(entity.getPosition());

        CategoryDto.setImageId(entity.getImage().getId());
        CategoryDto.setImageName(entity.getImage().getName());

        CategoryDto.setCreated(entity.getCreated());
        CategoryDto.setUpdated(entity.getUpdated());
        return CategoryDto;
    }

}
