package com.carlosdev.DemandManager.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.carlosdev.DemandManager.model.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenConfig {

    private final Algorithm algorithm;
    private final String secret;

    public TokenConfig(@Value("${JWT_SECRET}") String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("Segredo JWT (JWT_SECRET) não pode ser nulo ou vazio");
        }
        this.secret = secret;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(UserAuth userAuth) {
        return JWT.create().withClaim("userId", userAuth.getUserId().toString())
                .withSubject(userAuth.getEmail())
                .withExpiresAt(Instant.now()
                        .plusSeconds(86400)
                ).withIssuedAt(Instant.now())
                .sign(this.algorithm);
    }

    public Optional<JwTUserData> validateToken(String token) {
        try {
//            Algorithm algorithm = Algorithm.HMAC256("${JWT_SECRET}");
            DecodedJWT decode = JWT.require(this.algorithm).build().verify(token);
            return Optional.of(new JwTUserData(
                    UUID.fromString( decode.getClaim("userId").asString()),
                    decode.getSubject()
                    ));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
