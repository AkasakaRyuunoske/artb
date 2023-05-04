package com.maid.cafe.c.maidcafec.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
public class JWTUtils {
    private final String secret = "dsadsasdasda";

    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject){
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        log.info("generated token is: " + token);
        log.info("subject of token is: " + subject);
        log.info("role is: " + claims.get("role"));

        return token;
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
        log.info("Token : " + token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
