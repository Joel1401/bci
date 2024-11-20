package com.bci.integration.security;

import com.bci.integration.controller.dto.SignUpRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "user-api";
    public static final String TOKEN_AUDIENCE = "user-app";

    @Value("${app.jwt.secret}")
    private  String jwtSecret;

    @Value("${app.jwt.expiration.minutes}")
    private Long jwtExpirationMinutes;

    public String generate(SignUpRequest signUpRequest) {

        byte[] signingKey = jwtSecret.getBytes();

        return Jwts.builder()
            .header().add("typ", TOKEN_TYPE)
            .and()
            .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512)
            .expiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant()))
            .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .id(UUID.randomUUID().toString())
            .issuer(TOKEN_ISSUER)
            .audience().add(TOKEN_AUDIENCE)
            .and()
            .subject(signUpRequest.getUserName())
            .claim("name", signUpRequest.getName())
            .claim("preferred_username", signUpRequest.getUserName())
            .claim("email", signUpRequest.getEmail())
            .compact();
    }

    public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            byte[] signingKey = jwtSecret.getBytes();

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty();
    }


}
