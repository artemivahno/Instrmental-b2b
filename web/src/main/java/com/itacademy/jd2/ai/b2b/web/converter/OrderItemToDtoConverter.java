package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.web.dto.OrderItemDTO;

@Component
public class OrderItemToDtoConverter implements Function<IOrderItem, OrderItemDTO> {

    @Override
    public OrderItemDTO apply(final IOrderItem entity) {
        final OrderItemDTO dto = new OrderItemDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setOrdetObjectId(entity.getOrderObject().getId());
        dto.setOrdetObjectClose(entity.getOrderObject().getClose());
        dto.setProductId(entity.getProduct().getId());
        dto.setProductName(entity.getProduct().getName());
        dto.setProductPrice(entity.getProduct().getPrice());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        return dto;
    }

}
