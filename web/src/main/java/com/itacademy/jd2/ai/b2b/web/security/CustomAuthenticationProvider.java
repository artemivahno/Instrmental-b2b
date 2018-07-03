package com.itacademy.jd2.ai.b2b.web.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.service.IUserService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        final String email = authentication.getPrincipal() + "";
        final String password = authentication.getCredentials() + "";

        final IUser user = userService.getByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("1000");
        }
        if (!user.getEnabled()) { // locked user
            throw new DisabledException("1001");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("1000");
        }

        final int userId = user.getId();

        final List<SimpleGrantedAuthority> roles = Arrays
                .asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
        /* .asList(new SimpleGrantedAuthority(user.getRole().name())); */

        return new ExtendedUsernamePasswordAuthenticationToken(userId, email, password,
                roles);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
