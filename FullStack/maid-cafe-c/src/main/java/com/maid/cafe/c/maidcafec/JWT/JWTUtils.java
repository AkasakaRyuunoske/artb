package com.maid.cafe.c.maidcafec.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtils {
    private String secret = "NEE20ANATA20WA20ITSUMO20YUME20WO20MITE20MASU20KA?";

    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //10 hours
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    // Extraction Methods
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims CLAIMS = exctractAllClaims(token);

        return claimsResolver.apply(CLAIMS);
    }

    public Claims exctractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Validation Methods
    private Boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
