package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->request.anyRequest().authenticated())

//        Default Ui-FormLogin Page
//        http.formLogin(Customizer.withDefaults());

//        Allows us to access from postMan
                .httpBasic(Customizer.withDefaults())

//        Every time we need to pass credentials while we access new resources
        .sessionManagement(
                session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

//        our own userDetailsService
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1=User
//                .withDefaultPasswordEncoder()
//                .username("vrish")
//                .password("vrish")
//                .roles("admin")
//                .build();
//        UserDetails user2=User
//                .withDefaultPasswordEncoder()
//                .username("janane")
//                .password("janane")
//                .roles("admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
}
