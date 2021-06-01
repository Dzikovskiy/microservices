package by.dzikovskiy.usermicroservice.entity;

import lombok.Data;

@Data
public class UserAuth {
    private String user;
    private String password;
    private String token;
}
