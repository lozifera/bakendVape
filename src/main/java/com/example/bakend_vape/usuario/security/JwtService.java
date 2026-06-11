package com.example.bakend_vape.usuario.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkey";

    public  String generateToken(String email) {
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
    }

    public String extracUsername(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token, String email) {

        return extracUsername(token).equals(email);
    }

}
