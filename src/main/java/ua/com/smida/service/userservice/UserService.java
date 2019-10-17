package ua.com.smida.service.userservice;

import java.util.List;
import ua.com.smida.model.user.User;

public interface UserService {

    User registration(User user);

    User findUserByLogin(String login);

    User initJson(User user);

    List<User> allUser();
}
