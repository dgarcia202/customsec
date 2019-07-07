package com.github.dgarcia202.customsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity(debug = true)
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SiteMinderAuthenticationFilter siteMinderAuthenticationFilter;

    //@Autowired
    //private StreamlineUserManagementAuthProvider authProvider;

    @Autowired
    private StreamlineUserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManagerBean())
                //.authenticationProvider(authProvider)
                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/open-data").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterAt(siteMinderAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
