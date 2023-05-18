package citsk.ru.vipnet.entity.user;

import citsk.ru.vipnet.entity.AbstractEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private boolean enabled;
    private Role role;

    @ToString.Include(name = "password")
    private String passwordMask() {
        return "********";
    }
}
