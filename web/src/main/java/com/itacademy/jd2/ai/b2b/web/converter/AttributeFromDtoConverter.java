package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.service.IAttributeService;
import com.itacademy.jd2.ai.b2b.web.dto.AttributeDTO;

@Component
public class AttributeFromDtoConverter implements Function<AttributeDTO, IAttribute> {

    @Autowired
    private IAttributeService attributeService;

    @Override
    public IAttribute apply(final AttributeDTO dto) {
        final IAttribute entity = attributeService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }
}
