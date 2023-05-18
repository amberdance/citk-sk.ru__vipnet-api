package citsk.ru.vipnet.security;

import citsk.ru.vipnet.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

public class JwtHandler {

    private final String secret;

    public JwtHandler(String secret) {
        this.secret = secret;
    }


    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken)).onErrorResume(e ->
                Mono.error(new UnauthorizedException(e.getMessage())));
    }

    private VerificationResult verify(String accessToken) {
        var claims = getClaimsFromToken(accessToken);
        final Date expirationDate = claims.getExpiration();

        if (expirationDate.before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        return new VerificationResult(claims, accessToken);
    }

    private Claims getClaimsFromToken(String accessToken) {
        return Jwts.parser()
                   .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                   .parseClaimsJws(accessToken)
                   .getBody();
    }


    public static class VerificationResult {
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
