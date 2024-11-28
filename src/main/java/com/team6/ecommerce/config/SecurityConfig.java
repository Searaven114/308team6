
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with configuration
                .authorizeHttpRequests(req -> req
                        .requestMatchers(publicEndpoints()).permitAll() // Group public endpoints
                        .requestMatchers(anonymousEndpoints()).anonymous() // Group anonymous-only endpoints
                        .requestMatchers(adminEndpoints()).hasRole("ADMIN") // Admin-only endpoints
                        .requestMatchers(productManagerEndpoints()).hasAnyRole("ADMIN", "PRODUCTMANAGER") // Product manager endpoints
                        .requestMatchers(salesManagerEndpoints()).hasAnyRole("ADMIN", "SALESMANAGER") // Sales manager endpoints
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Customize login endpoint
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\":\"success\",\"message\":\"Login successful!\"}");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\":\"failure\",\"message\":\"Invalid username or password.\"}");
                        })
                )
                .httpBasic(withDefaults())
                .build();
    }


    private String[] publicEndpoints() {
        return new String[]{
                "/login",
                "/api/user",
                "/api/user/profile",
                "/",
                "/index",
                "/error",
                "/api/product/**",
                "/api/comments/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/v2/api-docs/**"
        };
    }


    private String[] anonymousEndpoints() {
        return new String[]{
                "/api/user/register"
        };
    }


    private String[] adminEndpoints() {
        return new String[]{
                "/api/admin/**",
                "/actuator/**",
                "/startup-report"
        };
    }


    private String[] productManagerEndpoints() {
        return new String[]{
                "/api/pm/**"
        };
    }


    private String[] salesManagerEndpoints() {
        return new String[]{
                "/api/sm/**"
        };
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow credentials (cookies, headers)
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Allow requests from the frontend
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow common HTTP methods
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply the CORS configuration globally
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}