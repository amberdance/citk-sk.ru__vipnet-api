package citsk.ru.vipnet.controller;

import citsk.ru.vipnet.dto.AuthRequestDto;
import citsk.ru.vipnet.dto.AuthResponseDto;
import citsk.ru.vipnet.dto.UserDto;
import citsk.ru.vipnet.mapper.UserMapper;
import citsk.ru.vipnet.security.CustomPrincipal;
import citsk.ru.vipnet.service.SecurityService;
import citsk.ru.vipnet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        var user = userMapper.map(dto);
        return userService.registerUser(user).map(userMapper::map);
    }

    @GetMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(),
                                      dto.getPassword())
                              .flatMap(tokenDetails ->
                                      Mono.just(AuthResponseDto.builder()
                                                               .userId(tokenDetails.getUserId())
                                                               .token(tokenDetails.getToken())
                                                               .issuedAt(tokenDetails.getIssuedAt())
                                                               .expiresAt(tokenDetails.getExpiresAt())
                                              .build()));
    }

    @GetMapping("/me")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        return userService.getUserById(((CustomPrincipal) authentication.getPrincipal()).getId()).map(userMapper::map);
    }

}
