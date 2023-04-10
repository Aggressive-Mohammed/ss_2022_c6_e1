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
                //.mvcMatchers("/demo/**").hasAnyAuthority("read")//this means all endpoints that comes after /demo/ must have the authority "read" before they can access the endpoint. eg /demo/test1/anything or /demo/test2/anything
                //.mvcMatchers("/test1").authenticated()
                //.mvcMatchers("/test2").hasAnyAuthority("re ad")
                .mvcMatchers(HttpMethod.GET,"/demo/**").hasAnyAuthority("read")//here the same rule is applied but for only GET requests
                .anyRequest().authenticated()
                .and().csrf().disable()//Don't Do this in real app because you anc introduce vulnurability in your app

                .build();
      }
}
