package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.web.dto.BrandDTO;

@Component
public class BrandToDtoConverter implements Function<IBrand, BrandDTO> {

    @Override
    public BrandDTO apply(final IBrand entity) {
        final BrandDTO brandDto = new BrandDTO();
        brandDto.setId(entity.getId());
        brandDto.setName(entity.getName());
        brandDto.setDescription(entity.getDescription());
        brandDto.setImageId(entity.getImage().getId());
        brandDto.setImageName(entity.getImage().getName());
        brandDto.setCreated(entity.getCreated());
        brandDto.setUpdated(entity.getUpdated());
        return brandDto;
    }

}
