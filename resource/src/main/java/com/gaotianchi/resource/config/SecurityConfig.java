package com.gaotianchi.resource.config;

import com.gaotianchi.resource.web.controller.CustomAccessDeniedHandler;
import com.gaotianchi.resource.web.controller.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/users/new").permitAll()
                                .requestMatchers("/users/**").hasRole("SUBSCRIBER")
                                .requestMatchers("/blogger/**").hasRole("BLOGGER")
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults()))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(customAuthenticationEntryPoint)  // 捕获认证异常
                        .accessDeniedHandler(customAccessDeniedHandler)  // 捕获授权异常
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
