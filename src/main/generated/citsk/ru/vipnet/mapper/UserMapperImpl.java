package citsk.ru.vipnet.mapper;

import citsk.ru.vipnet.dto.UserDto;
import citsk.ru.vipnet.entity.user.User;
import citsk.ru.vipnet.entity.user.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-18T15:51:22+0300",
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

        userDto.setId( user.getId() );
        userDto.setCreatedAt( user.getCreatedAt() );
        userDto.setUpdatedAt( user.getUpdatedAt() );
        userDto.setUsername( user.getUsername() );
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

        UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.createdAt( userDto.getCreatedAt() );
        user.updatedAt( userDto.getUpdatedAt() );
        user.username( userDto.getUsername() );
        user.password( userDto.getPassword() );
        user.firstName( userDto.getFirstName() );
        user.lastName( userDto.getLastName() );
        user.patronymic( userDto.getPatronymic() );
        user.enabled( userDto.isEnabled() );
        user.role( userDto.getRole() );

        return user.build();
    }
}
