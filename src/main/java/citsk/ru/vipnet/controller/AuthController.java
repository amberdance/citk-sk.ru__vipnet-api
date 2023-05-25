package citsk.ru.vipnet.controller;

import citsk.ru.vipnet.domain.dto.AuthRegisterRequestDto;
import citsk.ru.vipnet.domain.dto.AuthRequestDto;
import citsk.ru.vipnet.domain.dto.AuthResponseDto;
import citsk.ru.vipnet.domain.dto.UserDto;
import citsk.ru.vipnet.mapper.UserMapper;
import citsk.ru.vipnet.service.security.AuthService;
import citsk.ru.vipnet.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody AuthRegisterRequestDto request) {
        log.info("data: {}", request);

        return authService.register(request);
    }

    @GetMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshUserToken(request, response);
    }

    @GetMapping("/me")
    public UserDto getUserInfo(Authentication authentication) {
        return userMapper.toDto(userService.getUserById(((UserDto) authentication.getPrincipal()).getId()));
    }

}
