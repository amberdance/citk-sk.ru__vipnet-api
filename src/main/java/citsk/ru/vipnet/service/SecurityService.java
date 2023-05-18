package citsk.ru.vipnet.service;

import citsk.ru.vipnet.entity.user.User;
import citsk.ru.vipnet.exception.AuthException;
import citsk.ru.vipnet.security.TokenDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;


@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private int expiration;


    private TokenDetails generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());

        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims,
                                       String subject) {

        long expirationTimeInMillis = expiration * 1000L;
        var exiprationDate =
                new Date(new Date().getTime() + expirationTimeInMillis);

        return generateToken(exiprationDate, claims, subject);
    }

    private TokenDetails generateToken(Date expirationDate, Map<String,
            Object> claims, String subject) {
        var createdDate = new Date();
        var token =
                Jwts.builder().setId(UUID.randomUUID().toString())
                    .setClaims(claims)
                    .setIssuer(issuer)
                    .setSubject(subject)
                    .setIssuedAt(createdDate)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder()
                                                              .encodeToString(secret.getBytes()))
                    .compact();


        return TokenDetails.builder()
                           .token(token)
                           .issuedAt(createdDate)
                           .expiresAt(expirationDate)
                .build();
    }

    public Mono<TokenDetails> authenticate(String username, String password) {
        return userService.getUserByUsername(username).flatMap(user -> {
            if (!user.isEnabled()) {
                return Mono.error(new AuthException("User is disabled",
                        "USER_ACCOUNT_DISABLED"));
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return Mono.error(new AuthException("Invalid password",
                        "INVALID_PASSWORD"));
            }

            return Mono.just(generateToken(user).toBuilder().userId(user.getId())
                    .build());
        }).switchIfEmpty(Mono.error(new AuthException("User not found",
                "USER_NOT_FOUND")));
    }
}
