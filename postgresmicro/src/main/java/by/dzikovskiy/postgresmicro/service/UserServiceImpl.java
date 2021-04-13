package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.UserDao;
import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserResponse save(UserDto user) {
        return userDao.save(user);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public UserDto update(UserDto user) {
        return userDao.update(user);
    }
}
