package com.example.ss_2022_c6_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define a bean for PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define a bean for UserDetailsService that will load user details from in-memory storage
    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        // Define two users, one with "write" authority and the other with "read" authority
        var user1 = User.withUsername("mohammed").password(passwordEncoder().encode("1234")).authorities("write").build();
        var user2 = User.withUsername("ibrahim").password(passwordEncoder().encode("1234")).authorities("read").build();

        // Add both users to the in-memory storage
        uds.createUser(user1);
        uds.createUser(user2);

        return uds;
    }

    // Define a filter chain that handles HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().and().authorizeRequests()
                //.mvcMatchers("/demo/**").hasAnyAuthority("read") // Require "read" authority for all endpoints that come after "/demo/"
                //.mvcMatchers("/test1").authenticated() // Require authentication for "/test1" endpoint
                //.mvcMatchers("/test2").hasAnyAuthority("re ad") // Require "re ad" authority for "/test2" endpoint
                .mvcMatchers(HttpMethod.GET, "/demo/**").hasAnyAuthority("read") // Require "read" authority for all GET requests that come after "/demo/"
                .anyRequest().authenticated() // Require authentication for all other requests
                .and().csrf().disable() // Disable CSRF protection (for demonstration purposes only, do not use in production code)
                .build();
    }
}
