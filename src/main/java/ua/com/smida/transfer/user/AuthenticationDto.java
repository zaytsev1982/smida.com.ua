package ua.com.smida.transfer.user;

import lombok.Data;

@Data
public class AuthenticationDto {

    private String login;
    private String password;

}
