package com.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login","/api/events","/api/events/*"
                                ,"/api/tickets","/api/tickets/*","/api/tickets/user/*","/api/tickets/*/cancel","/api/notifications",
                                "/api/notifications/*","/api/feedback",
                                "/api/feedback/*","/api/feedback/event/*",
                                "/api/feedback/user/*",
                                "/api/feedback/eventRating/*","/api/feedback/event-rating/*").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
