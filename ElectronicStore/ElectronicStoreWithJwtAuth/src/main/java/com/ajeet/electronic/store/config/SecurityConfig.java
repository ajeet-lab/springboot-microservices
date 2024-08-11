package com.ajeet.electronic.store.config;


import org.modelmapper.internal.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig {
    private static final String USER_ROUTE = "/api/v1/users/**";
    private static final String PRODUCT_ROUTE = "/api/v1/products/**";
    private static final String CATEGORY_ROUTE = "/api/v1/category/**";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_NORMAL = "NORMAL";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET, USER_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.POST, USER_ROUTE).hasRole(ROLE_NORMAL)
                                .requestMatchers(HttpMethod.DELETE, USER_ROUTE).hasRole(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, USER_ROUTE).hasAnyRole(ROLE_NORMAL, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, PRODUCT_ROUTE).permitAll()
                                .requestMatchers(PRODUCT_ROUTE).hasRole(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, CATEGORY_ROUTE).permitAll()
                                .requestMatchers(CATEGORY_ROUTE).hasRole(ROLE_ADMIN)
                                .anyRequest().permitAll()
                );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
