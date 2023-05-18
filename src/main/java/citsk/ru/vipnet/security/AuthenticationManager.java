package citsk.ru.vipnet.security;

import citsk.ru.vipnet.entity.user.User;
import citsk.ru.vipnet.exception.UnauthorizedException;
import citsk.ru.vipnet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        var principal =
                (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(principal.getId())
                          .filter(User::isEnabled)
                          .switchIfEmpty(Mono.error(new UnauthorizedException("User disabled")))
                          .map(user -> authentication);

    }
}
