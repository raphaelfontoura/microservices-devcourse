package com.rddev.hroauth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;
import java.util.function.Function;


public class JwtUtils {

    private static Algorithm algorithm = Algorithm.HMAC256("my-secret-key".getBytes());
    private static String issuer = "http://localhost:8765";
    static JWTVerifier verifier = JWT.require(algorithm).build();

    public static String generateToken(String username, List<String> roles) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public static String generateRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public static String getUsername(String token) {
        DecodedJWT decodedJWT = verifier.verify(getToken(token));
        return decodedJWT.getSubject();
    }

    public static List<String> getRoles(String token) {
        DecodedJWT decodedJWT = verifier.verify(getToken(token));
        return decodedJWT.getClaim("roles").asList(String.class);
    }

    public static String[] getRolesArray(String token) {
        DecodedJWT decodedJWT = verifier.verify(getToken(token));
        return decodedJWT.getClaim("roles").asArray(String.class);
    }

    public static String getToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

}
