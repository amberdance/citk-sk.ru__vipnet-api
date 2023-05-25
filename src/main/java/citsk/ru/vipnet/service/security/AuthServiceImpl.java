package citsk.ru.vipnet.service.security;

import citsk.ru.vipnet.domain.dto.AuthRegisterRequestDto;
import citsk.ru.vipnet.domain.dto.AuthRequestDto;
import citsk.ru.vipnet.domain.dto.AuthResponseDto;
import citsk.ru.vipnet.domain.model.JwtToken;
import citsk.ru.vipnet.domain.model.Role;
import citsk.ru.vipnet.domain.model.User;
import citsk.ru.vipnet.repository.JwtTokenRepository;
import citsk.ru.vipnet.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final JwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponseDto register(AuthRegisterRequestDto requestDto) {
        var user = User.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .username(requestDto.getUsername())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .patronymic(requestDto.getPatronymic())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        var savedUser = userService.saveUser(user);
        var accessToken = jwtTokenService.generateToken(user);
        var refreshToken = jwtTokenService.generateRefreshToken(user);

        revokeUserTokens(user);
        saveUserToken(savedUser, accessToken);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String accessToken) {
        var token = JwtToken.builder()
                .user(user)
                .token(accessToken)
                .expired(false)
                .revoked(false)
                .build();

        jwtTokenRepository.save(token);
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto requestDto) {
        return null;
    }

    @Override
    public void revokeUserTokens(User user) {
        var tokens = jwtTokenRepository.findAllValidTokenByUser(user.getId());

        if (tokens.isEmpty()) {
            return;
        }

        tokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        jwtTokenRepository.saveAll(tokens);
    }

    @Override
    public void refreshUserToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtTokenService.extractSubject(refreshToken);

        if (username != null) {
            var user = userService.getUserByUsername(username);

            if (jwtTokenService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtTokenService.generateToken(user);

                revokeUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
