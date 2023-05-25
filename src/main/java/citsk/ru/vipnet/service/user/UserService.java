package citsk.ru.vipnet.service.user;

import citsk.ru.vipnet.domain.model.User;

public interface UserService {

    User saveUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    Iterable<User> getAllUsers();
}
