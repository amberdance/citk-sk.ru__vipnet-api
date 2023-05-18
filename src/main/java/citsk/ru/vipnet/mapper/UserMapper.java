package citsk.ru.vipnet.mapper;

import citsk.ru.vipnet.dto.UserDto;
import citsk.ru.vipnet.entity.user.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user);

    @InheritInverseConfiguration
    User map(UserDto userDto);

}
