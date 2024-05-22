package com.example.m183project.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.m183project.service.dto.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthProvider {
    /**
     * Beispiel für sichere JWT creation und validation
     */

//    @Value("${security.jwt.token.secret-key:secret-key}")
//    private String secretKey;
//
//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }
//
//    @Value("${kiosk.token.ttl:432000000}")
//    private String tokenValidity;
//
//    public String createToken(UserDTO userDTO) {
//        Date now = new Date();
//        return JWT.create()
//                .withIssuer(userDTO.getUsername())
//                .withIssuedAt(now)
//                .withExpiresAt(new Date(now.getTime() + Long.parseLong(tokenValidity)))
//                .sign(Algorithm.HMAC256(secretKey));
//    }
//
//    public Authentication validateToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decoded = verifier.verify(token);
//        UserDTO user = UserDTO.builder().username(decoded.getIssuer()).build();
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }

    /**
     * Schlechtes Beispiel mit Token, das einfach erraten werden kann und nicht abläuft
     */
    public String createToken(UserDTO userDTO) {
        String token = "token-" + userDTO.getUsername();
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    public Authentication validateToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        if (decodedToken.startsWith("token-")) {
            String username = decodedToken.substring(6);
            UserDTO user = UserDTO.builder().username(username).build();
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }
        return null;
    }
}
