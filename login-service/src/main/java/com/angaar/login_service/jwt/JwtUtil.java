package com.angaar.login_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    private int jwtExpirationMs = 6000000;  // 1 day in milliseconds
    
    @Value("${JWT_SECRET}")
    private String jwtSecret;       
    
    public JwtUtil() {
    	System.out.println("secret: " + jwtSecret);
    }

    // Create the signing key using the JWT secret
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
    
    public int getJwtExpirationMs() {
    	return this.jwtExpirationMs;
    }

    // Generate token using Authentication object
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();  // Get username from Authentication
        System.out.println("priniciál "+ userPrincipal);
        
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())  // Set the username as the subject of the token
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Sign the token with the generated key
            .compact();
    }
    
    public String generateToken(Authentication authentication, String id) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();  // Get username from Authentication
        System.out.println("priniciál "+ userPrincipal);
        
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())  // Set the username as the subject of the token
            .claim("id", id)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Sign the token with the generated key
            .compact();
    }

    // Extract claims from the JWT token (for validation)
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())  // Use the same key for validation
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Get the username (subject) from the token
    public String getUsernameFromToken(String token) {
    	System.out.println("Claims: " + getClaimsFromToken(token));
        return getClaimsFromToken(token).getSubject();
    }
    
    public String getUserIdFromToken(String token) {    	
        return getClaimsFromToken(token).getId();
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Use the same key for validation
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;  // Token is invalid
        }
    }

    // Extract the JWT token from the Authorization header
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove the "Bearer " prefix
        }
        return null;
    }
    
 // Parse JWT token from Cookies
    public String parseJwt(HttpServletRequest request) {
    	System.out.println("parsing request");
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
        	System.out.println("Found cookifes " + request.getCookies());
            for (Cookie cookie : cookies) {
            	System.out.println("cooke2: " + cookie.getName());
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
