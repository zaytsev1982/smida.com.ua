package ua.com.smida.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserLoginNotFoundException extends UsernameNotFoundException {

    public UserLoginNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserLoginNotFoundException(String msg) {
        super(msg);
    }
}
