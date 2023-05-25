package citsk.ru.vipnet.domain.dto;

import citsk.ru.vipnet.domain.model.Role;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthRegisterRequestDto {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String username;
    private String password;
    private boolean enabled;
    private Role role;

}
