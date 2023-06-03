package com.eslam.poeauditor.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.domain.JWTTokenDto;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Samson Effes
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class JWTService {

    @Value("${spring.jwt.secret}")
    private  String jwtSecret;

    @Value("${spring.jwt.jwt.expiration.ms}")
    private int jwtExpirationTimeMilliseconds;

    public JWTTokenDto generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return tokenCreator(claims, userName);
    }

    public JWTTokenDto tokenCreator(Map<String, Object> claims, String userName){
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(System.currentTimeMillis()+jwtExpirationTimeMilliseconds);
        String token = Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(issuedAt)
        .setExpiration(expiresAt)
        .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
        return JWTTokenDto.builder().issuedAt(issuedAt).expiresAt(expiresAt).token(token).build();
    }

    public String extractUsernameFromToken(String theToken){
        return extractClaim(theToken, Claims ::getSubject);
    }
    public Date extractExpirationTimeFromToken(String theToken) {
        return extractClaim(theToken, Claims :: getExpiration);
    }

    public Boolean validateToken(String theToken, UserDetails userDetails){
        final String userName = extractUsernameFromToken(theToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(theToken));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    private boolean isTokenExpired(String theToken) {
        return extractExpirationTimeFromToken(theToken).before(new Date());
    }
    private Key getSignedKey(){
        byte[] keyByte = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyByte);
    }

}