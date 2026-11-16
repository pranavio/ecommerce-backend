package com.pranav.ecommers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.BeanProperty;
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/users")
        )
                .authorizeHttpRequests(auth -> auth

                // Public authentication endpoints
                .requestMatchers(HttpMethod.POST,"/api/users").permitAll()

                // Anyone can view products
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

                // All remaining requests require authentication
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
