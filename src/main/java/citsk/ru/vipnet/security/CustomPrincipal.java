package citsk.ru.vipnet.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPrincipal implements Principal {

    private Long id;
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
