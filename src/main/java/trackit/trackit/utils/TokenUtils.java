package trackit.trackit.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import trackit.trackit.entity.AppUser;

@Component
public class TokenUtils {

    // private String secreteKey = "YOUR_SECRET_KEY";
    private String secreteKey = "aGVsbG93b3JsZHNlY3JldA=="; // "helloworldsecret" encoded in Base64

    byte[] apiKeySecretBytes = Base64.getDecoder().decode(secreteKey); // Decode if it's Base64 encoded
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

    public String generateToken(AppUser appUser) {
        return Jwts.builder().setSubject(appUser.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }
}
