package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.service.IOrderItemService;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;
import com.itacademy.jd2.ai.b2b.service.IProductService;
import com.itacademy.jd2.ai.b2b.web.dto.OrderItemDTO;

@Component
public class OrderItemFromDtoConverter implements Function<OrderItemDTO, IOrderItem> {

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderObjectService orderObjectService;

    @Autowired
    private IProductService productService;

    @Override
    public IOrderItem apply(final OrderItemDTO dto) {
        final IOrderItem entity = orderItemService.createEntity();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());

        final IOrderObject orderObject = orderObjectService.createEntity();
        orderObject.setId(dto.getOrderObjectId());
        entity.setOrderObject(orderObject);

        final IProduct model = productService.createEntity();
        model.setId(dto.getProductId());
        entity.setProduct(model);

        return entity;
    }

}
