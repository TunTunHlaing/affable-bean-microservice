package com.example.apisecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class Jwt {

    @Getter
    private final String token;
    @Getter
    private final Long userId;
    @Getter
    private final LocalDateTime issuedAt;
    @Getter
    private final LocalDateTime expiredAt;

    private Jwt(String token, Long userId, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.token = token;
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static Jwt of(Long userId, Long validInMinute, String securityKey){
        var issuedDDate = Instant.now();
        var expiration = issuedDDate.plus(validInMinute, ChronoUnit.MINUTES);
        return new Jwt(
                Jwts.builder()
                        .claim("user_id",userId)
                        .setIssuedAt(Date.from(issuedDDate))
                        .setExpiration(Date.from(expiration))
                        .signWith(SignatureAlgorithm.HS256,
                                Base64.getEncoder()
                                        .encodeToString(securityKey.getBytes(StandardCharsets.UTF_8)))
                        .compact(),userId,
                LocalDateTime.ofInstant(issuedDDate, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(expiration,ZoneId.systemDefault())
        );
    }

    public static Jwt from(String token, String secretKey){
        var claim = (Claims) Jwts.parserBuilder().setSigningKey(
                Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8))
        ).build()
                .parse(token)
                .getBody();
        var userId = claim.get("user_id",Long.class);
        var issuedAt = claim.getIssuedAt();
        var expiration = claim.getExpiration();

        return new Jwt(token,
                userId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(issuedAt.getTime())
                        ,ZoneId.systemDefault()),
        LocalDateTime.ofInstant(Instant.ofEpochMilli(expiration.getTime()),
        ZoneId.systemDefault()));

    }
}
