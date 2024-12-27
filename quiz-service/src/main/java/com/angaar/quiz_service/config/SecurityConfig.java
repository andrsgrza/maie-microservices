package com.angaar.quiz_service.config;

import com.angaar.quiz_service.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // Enable CORS
            .and()
            .csrf(csrf -> csrf.disable())  // Disable CSRF if not required for cookies in the current setup
            .authorizeHttpRequests()
            .requestMatchers("/api/quizzes/**").authenticated()  // Protect all quiz endpoints
            .requestMatchers("/api/resource-entitlement/**").authenticated()  // Protect resource-entitlement
            .requestMatchers(HttpMethod.DELETE, "/api/resource-entitlement/**").permitAll()
            .anyRequest().permitAll()  // Allow all other requests without authentication
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // Stateless for JWT

        // Add JWT filter to process authentication before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
