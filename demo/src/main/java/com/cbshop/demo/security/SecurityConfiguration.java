package com.cbshop.demo.security;

import com.cbshop.demo.exceptions.RestAccessDeniedHandler;
import com.cbshop.demo.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final ApplicationConfig applicationConfig;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "/error")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/order/**", "/api/v1/order/basket")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/order/by-user-id/**", "/api/v1/users/**", "/api/v1/products", "/api/v1/products/**")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/order/**", "/api/v1/order").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, "/api/v1/order/**", "/api/v1/products/**", "/api/v1/users/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**", "/api/v1/users/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1)
                .and()
                .sessionFixation()
                .migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authenticationProvider(applicationConfig.authenticationProvider())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler);
        return http.build();
    }
}