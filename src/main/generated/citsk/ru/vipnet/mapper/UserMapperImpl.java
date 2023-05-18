package citsk.ru.vipnet.mapper;

import citsk.ru.vipnet.dto.UserDto;
import citsk.ru.vipnet.entity.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-18T10:25:50+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto map(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setCreatedAt( user.getCreatedAt() );
        userDto.setUpdatedAt( user.getUpdatedAt() );
        userDto.setLogin( user.getLogin() );
        userDto.setPassword( user.getPassword() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setPatronymic( user.getPatronymic() );
        userDto.setEnabled( user.isEnabled() );
        userDto.setRole( user.getRole() );

        return userDto;
    }

    @Override
    public User map(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedAt( userDto.getCreatedAt() );
        user.setUpdatedAt( userDto.getUpdatedAt() );
        user.setLogin( userDto.getLogin() );
        user.setPassword( userDto.getPassword() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setPatronymic( userDto.getPatronymic() );
        user.setEnabled( userDto.isEnabled() );
        user.setRole( userDto.getRole() );

        return user;
    }
}
