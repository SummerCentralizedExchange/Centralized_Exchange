package ru.spbstu.sce.accessControl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtCore {
    private final Logger logger = LoggerFactory.getLogger(JwtCore.class);

    @Value("${server.app.secret}")
    private String secretKey;
    @Value("${server.app.lifetime}")
    private int lifetime;

    public String generateToken(Authentication authentication) {
        logger.info("return value of principal {}", authentication.getPrincipal());
        User userDetails = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
