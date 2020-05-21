package authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.ZonedDateTime;
import javax.crypto.spec.SecretKeySpec;

public class JwtAuthenticationService {

    private final Key key;
    private final JwtParser jwtParser;

    private JwtAuthenticationService() {
        final String keyString = "chiave molto lunga ma soprattutto sicurissima e a prova di qualsiasi loacker";
        
        this.key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA512");
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        
    }
    private static JwtAuthenticationService instance = null; //new JwtAuthenticationService();
    
    public static JwtAuthenticationService getInstance() {
        if (instance == null)
            instance = new JwtAuthenticationService();
        
        return instance;
    }

    public Claims authenticate(String token) {
        try {
            return jwtParser.parseClaimsJws(token)
                    .getBody();
        } catch (JwtException ex) {
            throw new AuthenticationException(ex);
        }
    }

    public String generateToken(String email, int id, boolean negoziante, UriInfo uriInfo) {
        return Jwts.builder()
                .claim("id", id)
                .claim("negoziante", negoziante)
                .setSubject(email)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new java.util.Date())
                .setExpiration(java.util.Date.from(ZonedDateTime.now().plusMinutes(15L).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
