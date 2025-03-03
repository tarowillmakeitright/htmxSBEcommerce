package com.ecommerce.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/login", "/webjars/**", "/css/**").permitAll()
                        .anyRequest().authenticated() // Protect all other URLs
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/", true) // Redirect after successful login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Use CustomOAuth2UserService
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Specify logout endpoint
                        .logoutSuccessUrl("/logout-success") // Redirect after successful logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Clear cookies
                        .permitAll() // Allow access to logout for all
                );
        return http.build();
    }
}
