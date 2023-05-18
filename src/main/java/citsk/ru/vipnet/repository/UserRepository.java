package citsk.ru.vipnet.repository;

import citsk.ru.vipnet.entity.user.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findByUsername(String username);
}
