package com.gaotianchi.resource.config;

import com.gaotianchi.resource.web.controller.hook.BeforeRequestHookAccessDeniedHandler;
import com.gaotianchi.resource.web.controller.hook.BeforeRequestHookAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, BeforeRequestHookAuthenticationEntryPoint beforeRequestHookAuthenticationEntryPoint, BeforeRequestHookAccessDeniedHandler beforeRequestHookAccessDeniedHandler) throws Exception {
        http.cors(c -> {
            c.configurationSource(corsConfigurationSource());
        });
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/users/new").permitAll()
                                .requestMatchers("/users/**").hasRole("SUBSCRIBER")
                                .requestMatchers("/blogger/**").hasRole("BLOGGER")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults()))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(beforeRequestHookAuthenticationEntryPoint)  // 捕获认证异常
                        .accessDeniedHandler(beforeRequestHookAccessDeniedHandler)  // 捕获授权异常
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
