package by.dzikovskiy.usermicroservice.security.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String refreshToken;
}