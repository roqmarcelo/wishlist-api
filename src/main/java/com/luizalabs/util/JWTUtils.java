package com.luizalabs.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {

    private JWTUtils() {
    }

    private static String key = "c3VwZXJzZWNyZXQ=";

    public static final String TOKEN_HEADER = "Authorization";

    public static String create(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static void decode(String token) {
        Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}