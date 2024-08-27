package com.sumerge.spring.config;

import com.sumerge.spring.filter.AdminTokenAuthFilter;
import com.sumerge.spring.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final SecurityService securityService;

    @Autowired
    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/courses/add", "/courses/update/**", "/courses/delete/**").hasRole("ADMIN")  // Secure admin endpoints
                .antMatchers("/courses/discover").authenticated()  // Secure discover endpoint for authenticated users
                .anyRequest().permitAll()  // Permit all other requests
                .and()
                .addFilterBefore(new AdminTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
