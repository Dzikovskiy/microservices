package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
