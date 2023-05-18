package citsk.ru.vipnet.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class AbstractEntity {

    @Id
    private Long id;
}
