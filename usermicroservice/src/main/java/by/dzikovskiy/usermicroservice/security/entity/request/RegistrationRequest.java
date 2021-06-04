package by.dzikovskiy.usermicroservice.security.entity.request;

import by.dzikovskiy.usermicroservice.security.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    private Role role;
}