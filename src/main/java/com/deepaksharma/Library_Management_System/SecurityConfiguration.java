package com.deepaksharma.Library_Management_System;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/transactions/transaction/issue").hasAuthority("ADMIN")
                        .requestMatchers("/transactions/transaction/return").hasAuthority("ADMIN")
                        .requestMatchers("/authors/delete/author").hasAuthority("ADMIN")
                        .requestMatchers("/authors/add/author").hasAuthority("ADMIN")
                        .requestMatchers("/books/add/book").hasAuthority("ADMIN")
                        .requestMatchers("/books/delete/book").hasAuthority("ADMIN")
                        .requestMatchers("/users/add/student").hasAuthority("ADMIN,STUDENT")
                        .requestMatchers("/users/add/admin").hasAuthority("ADMIN")
                        .requestMatchers("/users/student/unblock").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
        );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}