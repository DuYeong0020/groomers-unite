package com.petstylelab.groomersunite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/users",
                        "/users/password",
                        "/users/email-verification",
                        "/users/recovery-verification",
                        "/users/email-verification/confirm",
                        "/users/recovery-verification/confirm",
                        "/users/login-id"
                ))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                HttpMethod.POST,
                                "/users",
                                "/users/email-verification",
                                "/users/recovery-verification",
                                "/users/email-verification/confirm",
                                "/users/recovery-verification/confirm"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/users/password"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/users/login-id"
                        ).permitAll()
                        .anyRequest().authenticated())
                .build();
    }

}