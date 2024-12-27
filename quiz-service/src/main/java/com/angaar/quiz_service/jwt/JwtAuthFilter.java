package com.angaar.quiz_service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the JWT token from cookies
    	System.out.println("Filtering");
        String token = jwtUtil.getTokenFromCookies(request);
        System.out.println("Hi there. tojek : " + token);        

        if (token != null) {
            try {
                // Extract claims from the token
                Claims claims = jwtUtil.extractClaims(token);
                String username = claims.getSubject();
                if(username == null) {
                	System.out.println("Username is null");
                } else {
                	System.out.println("Username is " + username);
                }

                // If the username is present and the user is not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Create an authentication token using the claims
                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication token in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Log or handle any token validation exceptions
                logger.warn("JWT validation failed", e);
                System.out.println("JWT VALIDATION FAILED");
            }
        } else {
        	logger.warn("Reveived a null token: " + token);
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
}
