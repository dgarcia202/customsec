package com.github.dgarcia202.customsec.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class SiteMinderAuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    public SiteMinderAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Checking Site minder header for user name");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String username = httpRequest.getHeader("SM_USER");
        if (username == null || username.trim().length() == 0) {
            logger.debug("Site minder header not present or content not valid");
            chain.doFilter(request, response);
            return;
        }

        try {
            if (authenticationIsRequired(username)) {
                StreamlineAuthentication authRequest = new StreamlineAuthentication(username);
                Authentication authResult = this.authenticationManager.authenticate(authRequest);
                logger.debug("Site minder authentication succeeded!");
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
        } catch (AuthenticationException e) {
            logger.debug("Site minder authentication failed");
            SecurityContextHolder.clearContext();
        }

        logger.debug("Now I should be going forward with the found username as the current user");
        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        return true;
    }
}
