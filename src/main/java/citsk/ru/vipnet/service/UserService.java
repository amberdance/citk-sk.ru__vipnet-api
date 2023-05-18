package citsk.ru.vipnet.service;

import citsk.ru.vipnet.entity.user.Role;
import citsk.ru.vipnet.entity.user.User;
import citsk.ru.vipnet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> registerUser(User user) {
        return userRepository.save(user.toBuilder()
                                       .password(passwordEncoder.encode(user.getPassword()))
                                       .role(Role.USER)
                                       .enabled(true)
                                       .createdAt(LocalDateTime.now())
                                       .updatedAt(LocalDateTime.now())
                .build()).doOnSuccess(usr -> log.info("User registered: {}",
                usr));
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
