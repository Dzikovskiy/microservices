package by.dzikovskiy.kafkamicro.service;


import by.dzikovskiy.kafkamicro.dao.UserDao;
import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public User save(UserDto user) {
        return userDao.save(user);
    }

}
