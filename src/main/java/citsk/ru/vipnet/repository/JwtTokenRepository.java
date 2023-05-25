package citsk.ru.vipnet.repository;

import citsk.ru.vipnet.domain.model.JwtToken;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
    @Query(value = """
            select t from user_tokens ut inner join users u
            on ut.user.id = u.id
            where u.id = :id and (ut.expired = false or ut.revoked = false)
            """)
    List<JwtToken> findAllValidTokenByUser(long id);

    Optional<JwtToken> findByToken(String token);
}
