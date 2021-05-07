package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.UserWithAuditDao;
import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceWithAuditImpl implements UserService {
    private final UserWithAuditDao userWithAuditDao;

    @Override
    public UserResponse save(UserDto user) {
        return userWithAuditDao.save(user);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userWithAuditDao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userWithAuditDao.deleteById(id);
    }

    @Override
    public UserDto update(UserDto user) {
        return userWithAuditDao.update(user);
    }
}
