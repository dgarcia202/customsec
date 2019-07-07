package com.github.dgarcia202.customsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class StreamlineUserManagementAuthProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String)authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Unable to retrieve details of user %s from UM. Authentication will fail", (String)authentication.getPrincipal()));
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(StreamlineAuthentication.class);
    }
}
