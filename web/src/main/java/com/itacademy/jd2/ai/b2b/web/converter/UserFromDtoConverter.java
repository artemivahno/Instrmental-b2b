package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.dto.UserDTO;

@Component
public class UserFromDtoConverter implements Function<UserDTO, IUser> {

    @Autowired
    private IUserService userService;

    @Override
    public IUser apply(final UserDTO dto) {
        final IUser entity = userService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setEnabled(dto.getEnabled());
        entity.setRole(UserRole.valueOf(dto.getRole()));
        return entity;
    }
}
