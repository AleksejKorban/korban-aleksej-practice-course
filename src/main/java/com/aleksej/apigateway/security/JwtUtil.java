package com.aleksej.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.nio.charset.StandardCharsets;

public class JwtUtil {

    private final String secret;
    private final String issuer;
    private final String audience;
    private final long leeway;

    public JwtUtil(String secret, String issuer, String audience, long leeway) {
        this.secret = secret;
        this.issuer = issuer;
        this.audience = audience;
        this.leeway = leeway;
    }

    public String validateAndGetSubject(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            if (!issuer.equals(claims.getIssuer())) {
                throw new SignatureException("Invalid issuer");
            }
            if (!audience.equals(claims.getAudience())) {
                throw new SignatureException("Invalid audience");
            }


            if (claims.getExpiration() != null &&
                    claims.getExpiration().getTime() < System.currentTimeMillis() - (leeway * 1000)) {
                throw new SignatureException("Token expired");
            }

            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}