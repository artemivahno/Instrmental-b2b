package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.web.dto.AttributeDTO;

@Component
public class AttributeToDtoConverter implements Function<IAttribute, AttributeDTO> {

    @Override
    public AttributeDTO apply(final IAttribute entity) {
        final AttributeDTO attributeDto = new AttributeDTO();
        attributeDto.setId(entity.getId());
        attributeDto.setName(entity.getName());
        attributeDto.setValue(entity.getValue());
        attributeDto.setCreated(entity.getCreated());
        attributeDto.setUpdated(entity.getUpdated());
        return attributeDto;
    }

}
