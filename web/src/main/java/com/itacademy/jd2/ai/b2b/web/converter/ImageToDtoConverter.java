package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.web.dto.ImageDTO;

@Component
public class ImageToDtoConverter implements Function<IImage, ImageDTO> {

    @Override
    public ImageDTO apply(final IImage entity) {
        final ImageDTO imageDto = new ImageDTO();
        imageDto.setId(entity.getId());
        imageDto.setName(entity.getName());
        imageDto.setUrl(entity.getUrl());
        imageDto.setPosition(entity.getPosition());
        imageDto.setCreated(entity.getCreated());
        imageDto.setUpdated(entity.getUpdated());
        return imageDto;
    }

}
