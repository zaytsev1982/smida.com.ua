package ua.com.smida.restcontroller.auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.smida.model.user.User;
import ua.com.smida.security.jwt.JwtTokenProvider;
import ua.com.smida.service.userservice.UserService;
import ua.com.smida.transfer.user.AuthenticationDto;

@RestController
@RequestMapping("/api/v1/")
public class AuthRestController {

    private final AuthenticationManager authenticate;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthRestController(
        AuthenticationManager authenticate,
        UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticate = authenticate;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("registration")
    public ResponseEntity<User> responseEntity(@RequestBody AuthenticationDto authenticationDto) {

        User candidate = User
            .builder()
            .login(authenticationDto.getLogin())
            .password(authenticationDto.getPassword())
            .build();
        User registration = userService.registration(candidate);


        return new ResponseEntity<>(registration, HttpStatus.CREATED);
    }


    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationDto candidate) {
        try {
            String username = candidate.getLogin();
            authenticate.authenticate(
                new UsernamePasswordAuthenticationToken(username,
                    candidate.getPassword()));
            User account = userService.findUserByLogin(username);

            if (account == null) {
                throw new UsernameNotFoundException(
                    "User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", "Bearer_" + token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
