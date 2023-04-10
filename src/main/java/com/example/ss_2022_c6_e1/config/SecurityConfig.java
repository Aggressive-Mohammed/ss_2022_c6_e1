package com.example.ss_2022_c6_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }


    @Bean
    public UserDetailsService userDetailsService(){
        var uds = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("mohammed")
                    .password(passwordEncoder().encode("1234"))
                    .authorities("write")
                    .build();

        var user2 = User.withUsername("ibrahim")
                        .password(passwordEncoder().encode("1234"))
                        .authorities("read")
                        .build();

        uds.createUser(user1);
        uds.createUser(user2);

        return uds;
    }


      @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()

                .and().build();
      }
}
