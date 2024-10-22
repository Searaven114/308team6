package com.team6.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req->{
                     req
                         .requestMatchers("/login","/api/user","/api/user/profile","/", "/index", "/error", "/api/user", "/register")
                         .permitAll()

                         // Admin API
                         .requestMatchers("/api/admin/**")
                         .hasRole("ADMIN")

                         // Product manager API
                         .requestMatchers("/api/pm/**")
                         .hasAnyRole("ADMIN","PRODUCTMANAGER")

                         // Sales manager API
                         .requestMatchers("/api/sm/**")
                         .hasAnyRole("ADMIN","SALESMANAGER")

                         // Actuator API
                         .requestMatchers("/actuator/**", "/startup-report")
                         .hasRole("ADMIN")

                        // API Documentation API
                        .requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v2/api-docs/**"
                        ).permitAll()

                        // Global error page
                        .requestMatchers("/error")
                                .permitAll()


                        .anyRequest().authenticated();

                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())
                .httpBasic(withDefaults())

                .build();
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
