package com.deepaksharma.Library_Management_System;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/transactions/transaction/issue").hasAuthority("ADMIN")
                    .requestMatchers("/transactions/transaction/return").hasAuthority("ADMIN")
                    .requestMatchers("/authors/delete/author").hasAuthority("ADMIN")
                    .requestMatchers("/authors/add/author").hasAuthority("ADMIN")
                    .requestMatchers("/books/add/book").hasAuthority("ADMIN")
                    .requestMatchers("/books/delete/book").hasAuthority("ADMIN")
                    .requestMatchers("/users/add/admin").permitAll()
                    .requestMatchers("/users/add/student").permitAll()
                    .requestMatchers("/users/student/unblock").hasAuthority("ADMIN")
                    .requestMatchers("/users/details").authenticated()  // Require authentication for this endpoint
                    .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());  // Enable HTTP Basic authentication
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}