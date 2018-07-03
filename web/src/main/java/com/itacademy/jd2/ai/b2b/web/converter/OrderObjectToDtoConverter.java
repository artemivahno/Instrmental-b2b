package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.web.dto.OrderObjectDTO;

@Component
public class OrderObjectToDtoConverter implements Function<IOrderObject, OrderObjectDTO> {

    @Override
    public OrderObjectDTO apply(final IOrderObject entity) {
        final OrderObjectDTO dto = new OrderObjectDTO();
        dto.setId(entity.getId());
        dto.setClose(entity.getClose());
        dto.setUserId(entity.getCreator().getId());
        dto.setUserName(entity.getCreator().getName());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        return dto;
    }

}
