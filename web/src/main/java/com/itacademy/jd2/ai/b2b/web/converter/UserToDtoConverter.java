package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.web.dto.UserDTO;

@Component
public class UserToDtoConverter implements Function<IUser, UserDTO> {

    @Override
    public UserDTO apply(final IUser entity) {
        final UserDTO userDto = new UserDTO();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setEnabled(entity.getEnabled());
        userDto.setRole(entity.getRole().name());
        userDto.setCreated(entity.getCreated());
        userDto.setUpdated(entity.getUpdated());
        return userDto;
    }

}
