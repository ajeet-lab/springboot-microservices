package com.ajeet.electronic.store.config;


import com.ajeet.electronic.store.helpers.AppConstents;
import com.ajeet.electronic.store.security.JwtAuthenticationEntryPoint;
import com.ajeet.electronic.store.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.internal.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter filter;

    private final JwtAuthenticationEntryPoint entryPoint;

    private final String[] PUBLIC_URLS = {"/swagger-ui/**", "/webjars/**", "/swagger-resources"};

    public SecurityConfig(JwtAuthenticationFilter filter, JwtAuthenticationEntryPoint entryPoint){
        this.filter = filter;
        this.entryPoint = entryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOriginPatterns(List.of("*"));
                        configuration.setAllowedMethods(List.of("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(List.of("*"));
                        configuration.setMaxAge(4000L);
                        return configuration;
                    }
                }));

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET, AppConstents.USER_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.POST, AppConstents.USER_ROUTE).hasRole(AppConstents.ROLE_NORMAL)
                                .requestMatchers(HttpMethod.DELETE, AppConstents.USER_ROUTE).hasRole(AppConstents.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, AppConstents.USER_ROUTE).hasAnyRole(AppConstents.ROLE_NORMAL, AppConstents.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, AppConstents.PRODUCT_ROUTE).permitAll()
                                .requestMatchers(AppConstents.PRODUCT_ROUTE).hasRole(AppConstents.ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, AppConstents.CATEGORY_ROUTE).permitAll()
                                .requestMatchers(AppConstents.CATEGORY_ROUTE).hasRole(AppConstents.ROLE_ADMIN)
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers("/auth/**").authenticated()
                                .anyRequest().permitAll()

                );
        // http.httpBasic(Customizer.withDefaults());
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
