package kea.alog.auth.utils;

import java.util.Date;

import io.jsonwebtoken.*;

public class JwtUtil {

    public static String createJwt(Long userPk, String userNN, String userEmail, String secretKey, Long expireMs) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        claims.put("userNN", userNN);
        claims.put("userPk", userPk);

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration()
                .before(new Date());

    }

    public static String getUserEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userEmail").toString();
    }

    public static Long getUserPk(String token, String secretKey) {
        return Long.parseLong(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userPk").toString());
    }

    public static String getUserNN(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userNN").toString();
    }
}