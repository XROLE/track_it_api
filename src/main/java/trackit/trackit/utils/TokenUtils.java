package trackit.trackit.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import trackit.trackit.entity.AppUser;

@Component
public class TokenUtils {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
    public String generateToken(AppUser appUser) {
        return Jwts.builder().setSubject(appUser.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
