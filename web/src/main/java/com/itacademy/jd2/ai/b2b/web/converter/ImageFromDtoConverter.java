package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.dto.ImageDTO;

@Component
public class ImageFromDtoConverter implements Function<ImageDTO, IImage> {

    @Autowired
    private IImageService imageService;

    @Override
    public IImage apply(final ImageDTO dto) {
        final IImage entity = imageService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setPosition(dto.getPosition());
        return entity;
    }
}
