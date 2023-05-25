package citsk.ru.vipnet.service.security;

import citsk.ru.vipnet.domain.dto.AuthRegisterRequestDto;
import citsk.ru.vipnet.domain.dto.AuthRequestDto;
import citsk.ru.vipnet.domain.dto.AuthResponseDto;
import citsk.ru.vipnet.domain.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public interface AuthService {

    AuthResponseDto register(AuthRegisterRequestDto requestDto);

    AuthResponseDto authenticate(AuthRequestDto requestDto);

    void revokeUserTokens(User user);

    void refreshUserToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
