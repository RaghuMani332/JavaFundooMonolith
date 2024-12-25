package com.bl.fundoo.security;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bl.fundoo.requestdto.UserRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JWTService {

	private String key="7ZlygC+L6YSJcByP5q+107ewDj78oE0VWGarhF7Oqgw=";

	public String generateToken(UserRequest request) {
		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("emailId" ,request.getEmailId());
		return Jwts.builder()
					.claims()
					.add(claims)
					.issuedAt(new Date(System.currentTimeMillis()))
					.subject(request.getFirstName())
					.expiration(new Date(System.currentTimeMillis()+ (60 * 60 * 1000 * 24)*(2) ))
					.and()
					.signWith(getKey())
					.compact();
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	 public Claims extractToken(String token) {
	        try {
	            Claims claims = Jwts.parser()
	                                .verifyWith((SecretKey) getKey())  // Validate using the same signing key
	                                .build()
	                                .parseSignedClaims(token)  // Parse the JWT token
	                                .getPayload();
	            return claims;  // Extract the subject (name) from the claims
	        } catch (JwtException | IllegalArgumentException e) {
	            // Handle exceptions (invalid token, expired token, etc.)
	            throw new RuntimeException("Invalid or expired token", e);
	        }
	    }

	public String extractUserName(String token) {
		return extractToken(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		Claims c= extractToken(token);
		final String userName=extractUserName(token);
		return(userName.equals(userDetails.getUsername()) && isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractToken(token).getExpiration().after(new Date());
	}

}
