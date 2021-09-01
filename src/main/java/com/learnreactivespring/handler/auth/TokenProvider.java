package com.learnreactivespring.handler.auth;

import static com.learnreactivespring.constants.EndpointConstants.*;

import com.learnreactivespring.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;


@Component
public class TokenProvider implements Serializable {


  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(SIGNING_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(User user) {

    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim(AUTHORITIES_KEY, null)
        .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
        .compact();
  }

}