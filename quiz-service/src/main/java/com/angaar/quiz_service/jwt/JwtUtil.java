package com.angaar.quiz_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${JWT_SECRET}")
    private String jwtSecret;
    
    
    public JwtUtil() {
    	System.out.println("Here is the secret" + (this.jwtSecret));
    }
    @PostConstruct
    public void init() {
        System.out.println("JWT_SECRET after initialization: " + this.jwtSecret);
    }


    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())  // Use the same key for validation
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
 // Create the signing key using the JWT secret
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractClaims(token).getSubject();
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    
    public String extractId(String token) {
    	if(extractClaims(token).get("id") == null) {
    		return null;
    	}
    	String extractedId = (String) extractClaims(token).get("id"); 
    	return String.valueOf(extractedId);

    }

    public String getTokenFromCookies(HttpServletRequest request) {
    	System.out.println("parsing request");
        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies: " + request.getCookies());
        
        if (cookies != null) {
        	System.out.println("Found cookifes " + request.getCookies());
            for (Cookie cookie : cookies) {
            	System.out.println("cooke: " + cookie.getName());
                if ("JWT_TOKEN".equals(cookie.getName())) {
                	System.out.println(("Cookie value"));
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
