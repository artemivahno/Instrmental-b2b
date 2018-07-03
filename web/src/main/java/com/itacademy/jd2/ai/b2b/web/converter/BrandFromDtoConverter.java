package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.IBrandService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.dto.BrandDTO;

@Component
public class BrandFromDtoConverter implements Function<BrandDTO, IBrand> {

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IImageService imageService;

    @Override
    public IBrand apply(final BrandDTO dto) {
        final IBrand entity = brandService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        final IImage image = imageService.createEntity();
        image.setId(dto.getImageId());
        entity.setImage(image);
        return entity;
    }
}
