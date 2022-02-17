package com.assignment.openweather.security;

import com.assignment.openweather.domain.model.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.security.core.context.ReactiveSecurityContextHolder.getContext;

@Component
@Slf4j
public class JwtTokenProvider {

    private SecretKey secretKey;

    @Value("${deepLink.jwe.signingKey}")
    private String jwtSigningKey;

    @PostConstruct
    public void init() {
        String secret = Base64.getEncoder().encodeToString(jwtSigningKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication) throws JoseException {
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(String.valueOf(authentication.getPrincipal()));
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(new AesKey(secretKey.getEncoded()));
        return jwe.getCompactSerialization();
    }

    public Authentication getAuthentication() {
        return getContext().blockOptional()
                .map(SecurityContext::getAuthentication)
                .orElseThrow(() -> new AuthorizationServiceException("Not Authorized"));
    }


    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleCode());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        long expirationTimeLong = Long.parseLong("3600");
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(this.secretKey)
            .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
