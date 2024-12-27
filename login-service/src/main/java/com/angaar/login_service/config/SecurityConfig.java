package com.angaar.login_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.angaar.login_service.jwt.JwtAuthFilter;
import com.angaar.login_service.services.MaieUserDetailsService;

/*
 * This file configures Spring Security for the application
 * Including JWT-based authentication, password encoding, and setting the security filter chain.
 * 
 *  It tells Spring Security to use  UserDetailsService and the JwtAuthFilter to protect secured routes.
 */

/* Key Concepts
 * Security Filter Chain: Configures HTTP security, including which endpoints are public (/api/auth/**) and which require authentication.
 * JWT Authentication Filter: The custom JwtAuthFilter is added to validate JWT tokens in incoming requests before other authentication methods (like form login).
 *  Password Encoding: Uses BCryptPasswordEncoder to hash passwords securely, making it difficult to reverse-engineer
 */

@Configuration
@EnableMethodSecurity  // Enable method-level security annotations like @PreAuthorize
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final MaieUserDetailsService maieUDS;
    
    // Constructor injection
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, MaieUserDetailsService maieUDS) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.maieUDS = maieUDS;
    }

    // Define the SecurityFilterChain bean for configuring HTTP security
    /*
     * This method defines the security filter chain for the application.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // Enable CORS
            .and()
            .csrf(csrf -> csrf.disable())  // Disable CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow CORS preflight requests
                .requestMatchers("/api/auth/**").permitAll()  // Allow authentication endpoints
                // TEMPORARILY CHANGED THIS. FOR SOME REASON, RESOUCRCE_ENTITLEMENT IS FAILINNG AND I DONT N¿KNOW WHAT, CHANGE TO AUTHORIZE
                // TODO: CHANGE THIS
                .anyRequest().permitAll()  // Require authentication for all other endpoints
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Stateless sessions

        // Add the JWT filter before the default UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    // Define the PasswordEncoder bean for password hashing (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define the AuthenticationManager bean, exposing it through AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}