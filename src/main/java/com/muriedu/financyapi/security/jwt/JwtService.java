package com.muriedu.financyapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.exceptions.ExpiredTokenException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signature;

    public String generateToken(UserEntity user){
        long expString = Long.parseLong(expiration);
        LocalDateTime dataHoraExpiration = LocalDateTime.now().plusMinutes(expString);
        Date date = Date.from(dataHoraExpiration.atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(signature));
    }
    public boolean validateToken( String token ){
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(signature))
                    .build()
                    .verify(token);

            Instant expiration = decodedJWT.getExpiresAt().toInstant();
            return !Instant.now().isAfter(expiration);
        }catch (Exception e){
            return false;
        }
    }

    public String getUserLogin( String token ) throws ExpiredTokenException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(signature))
                .build()
                .verify(token);

        if (validateToken(token)) return decodedJWT.getSubject();
        else return "";
    }
}