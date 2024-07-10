package ru.spbstu.sce.accesscontrol;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${server.app.secret}")
    private String secretKey;
    @Value("${server.app.lifetime}")
    private int tokenLifetimeMs;

    public String generateToken(Authentication authentication, Instant issuedAt) {
        logger.info("return value of principal {}", authentication.getPrincipal());
        User user = (User) authentication.getPrincipal();
        JwtBuilder builder = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(issuedAt.plusMillis(tokenLifetimeMs)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
        return builder.compact();
    }

    public String getNameFromJwt(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}
