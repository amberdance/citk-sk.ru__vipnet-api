package citsk.ru.vipnet.entity.user;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("users")
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Email
    private String username;
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
