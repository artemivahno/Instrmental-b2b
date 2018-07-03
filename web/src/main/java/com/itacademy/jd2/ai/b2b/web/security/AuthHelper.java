package com.itacademy.jd2.ai.b2b.web.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;

public class AuthHelper {

    private AuthHelper() {
    }

    public static Integer getLoggedUserId() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context.getAuthentication();
        if (auth != null && auth instanceof ExtendedUsernamePasswordAuthenticationToken) {
            final ExtendedUsernamePasswordAuthenticationToken authentication = (ExtendedUsernamePasswordAuthenticationToken) auth;
            return authentication.getId();
        } else {
            return null;
        }

    }

    public static boolean containsRole(final UserRole role) {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Collection<? extends GrantedAuthority> authorities = context
                .getAuthentication().getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_" + role.name())) {
                return true;
            }
        }
        return false;
    }
}
