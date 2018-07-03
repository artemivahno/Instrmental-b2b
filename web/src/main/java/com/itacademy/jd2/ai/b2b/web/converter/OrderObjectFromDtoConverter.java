package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.dto.OrderObjectDTO;

@Component
public class OrderObjectFromDtoConverter
        implements Function<OrderObjectDTO, IOrderObject> {

    @Autowired
    private IOrderObjectService orderService;

    @Autowired
    private IUserService userService;

    @Override
    public IOrderObject apply(final OrderObjectDTO dto) {
        final IOrderObject entity = orderService.createEntity();
        entity.setId(dto.getId());
        entity.setClose(dto.getClose());

        final IUser user = userService.createEntity();
        user.setId(dto.getUserId());
        entity.setCreator(user);
        return entity;
    }
}
