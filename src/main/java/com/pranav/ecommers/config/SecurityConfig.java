
package com.pranav.ecommers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        // Ignore CSRF only for public POST endpoints
        RequestMatcher publicPostEndpoints = request ->
                "POST".equals(request.getMethod()) &&
                        (
                                "/api/users".equals(request.getServletPath()) ||
                                        "/api/auth/login".equals(request.getServletPath())
                        );

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(publicPostEndpoints)
                )

                .authorizeHttpRequests(auth -> auth

                        // Public registration
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Public login
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Anyone can view products
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
