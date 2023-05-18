package citsk.ru.vipnet.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBFDK2PasswordEncoder implements PasswordEncoder {

    @Value("${jwt.password.encoder.secret}")
    private String secret;

    @Value("${jwt.password.encoder.iteration}")
    private int iteration;

    @Value("${jwt.password.encoder.keylength}")
    private int keyLength;
    private final String SECRET_KEY_INSTANCE = "PBKDF2WithHmacSHA512";

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            var ans = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE)
                    .generateSecret(new PBEKeySpec((rawPassword.toString()
                            .toCharArray()), secret.getBytes(), iteration,
                            keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(ans);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
