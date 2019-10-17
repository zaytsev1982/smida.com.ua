package ua.com.smida.service.userservice;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.smida.exception.UserLoginNotFoundException;
import ua.com.smida.model.user.Role;
import ua.com.smida.model.user.User;
import ua.com.smida.repository.userrepository.UserRepository;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPassword;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
        BCryptPasswordEncoder bCryptPassword) {
        this.userRepository = userRepository;
        this.bCryptPassword = bCryptPassword;
    }

    @Override
    public User registration(User user) {
        User candidate = User
            .builder()
            .login(user.getLogin())
            .password(bCryptPassword.encode(user.getPassword()))
            .roles(Collections.singleton(Role.USER))
            .build();

        User aCandidate = userRepository.save(candidate);
        log.info("in registration. user created successfully. Details {}", aCandidate);
        return aCandidate;
    }

    @Override
    public User findUserByLogin(String login) {
        User candidate = userRepository.findUserByLogin(login)
            .orElseThrow(
                () -> new UserLoginNotFoundException("user with login " + login + " not found"));
        log.info("in findByLogin. User with login {} find successfully. Info {}", login, candidate);
        return candidate;
    }

    @Override
    public User initJson(User user) {
        User candidate = User
            .builder()
            .login(user.getLogin())
            .password(bCryptPassword.encode(user.getPassword()))
            .roles(user.getRoles())
            .build();
        log.info("in initJson. User created successfully. Info {}", candidate);
        User save = userRepository.save(candidate);
        return save;
    }

    @Override
    public List<User> allUser() {
        List<User> users = userRepository.findAll();
        users.forEach(u -> {
            log.info("in allUser. Detail {}", u);
        });
        return users;
    }
}
