package by.dzikovskiy.kafkamicro.dao;

import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;
import by.dzikovskiy.kafkamicro.entity.Visa;
import by.dzikovskiy.kafkamicro.mapper.UserDtoMapper;
import by.dzikovskiy.kafkamicro.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserDao {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Transactional
    public User save(UserDto user) {
        User savedUser = userRepository.save(userDtoMapper.userDtoToUser(user));
        for (Visa t : savedUser.getVisas()) {
            t.setUser(savedUser);
        }

        savedUser = userRepository.save(savedUser);

        return savedUser;
    }
}
