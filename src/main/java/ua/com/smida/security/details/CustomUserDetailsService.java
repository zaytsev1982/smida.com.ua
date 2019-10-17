package ua.com.smida.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.smida.exception.UserLoginNotFoundException;
import ua.com.smida.model.user.User;
import ua.com.smida.repository.userrepository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(
        UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User account = userRepository.findUserByLogin(userName).orElseThrow(
            () -> new UsernameNotFoundException("account with user " + userName + " not found"));
        return new CustomUserDetails(account);
    }
}
