package cl.aguilera.projects.javabcintt.util;

import cl.aguilera.projects.javabcintt.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "jessyaguileralopezorgaguilerapruebabciijavatesting";

    private static final Long EXPIRATION_TIME = 86_400_000L;

    public static String generateToken(User user) {
        Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}
