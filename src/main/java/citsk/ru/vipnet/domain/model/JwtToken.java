package citsk.ru.vipnet.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_tokens")
public class JwtToken {

    @Id
    private Long id;

    private String token;

    private boolean revoked;

    private boolean expired;

    @MappedCollection(idColumn = "id")
    private User user;

}
