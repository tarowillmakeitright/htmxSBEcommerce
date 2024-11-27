package com.ecommerce.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/home", "/webjars/**", "/css/**", "/error").permitAll() // Public URLs
                        .anyRequest().authenticated() // Protect all other URLs
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/home", true) // Redirect after successful login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService((OAuth2UserService<OAuth2UserRequest, OAuth2User>) customOAuth2UserService) // Custom service to process user info
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout-success") // Endpoint for logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Clear cookies
                        .permitAll() // Allow access to logout for all
                );
        return http.build();
    }
}