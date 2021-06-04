package by.dzikovskiy.usermicroservice.service;


import by.dzikovskiy.usermicroservice.security.entity.UserAuth;
import by.dzikovskiy.usermicroservice.security.repository.UserAuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuth saveUser(UserAuth user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userAuthRepository.save(user);
    }

    public UserAuth findByLogin(String login) {
        return userAuthRepository.findByLogin(login).get();
    }

    public Optional<UserAuth> findByLoginAndPassword(String login, String password) {
        UserAuth userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return Optional.of(userEntity);
            }
        }
        return Optional.empty();
    }
}