package com.angaar.login_service.jwt;


import com.angaar.login_service.services.MaieUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MaieUserDetailsService myUserDetailsService;

    // Constructor-based dependency injection
    public JwtAuthFilter(JwtUtil jwtUtil, MaieUserDetailsService myUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
   
    	try {    		
    		String token = jwtUtil.parseJwt(request);    		
            String username = null;

            if(token != null) {
            	username = jwtUtil.getUsernameFromToken(token);
            }

            // Authenticate the request if the token is valid and user is not already authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);  // Load user from DB

                if (jwtUtil.validateToken(token)) {  // Validate token
                    // Set up authentication context
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);  // Continue the filter chain
        } catch (ExpiredJwtException e) {
            // Handle expired token
        	System.out.println("Expired token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token has expired");
            response.getWriter().flush();
        } catch (Exception e) {
            // Handle generic JWT parsing error
        	System.out.println("Error parsing token: " + e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error parsing JWT token");
            response.getWriter().flush();
        }
    }

    
}