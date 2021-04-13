package by.dzikovskiy.postgresmicro.dao;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import by.dzikovskiy.postgresmicro.service.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserWithAuditDao {
    private final UserDao userDao;
    private final AuditService auditService;

    @Transactional
    public UserResponse save(UserDto user) {
        UserResponse userResponse = userDao.save(user);
        auditService.save(auditService.generateAudit("Method save() called with user:" + userResponse));
        return userResponse;
    }

    @Transactional
    public Optional<UserDto> findById(Long id) {
        Optional<UserDto> user = userDao.findById(id);
        auditService.save(auditService.generateAudit("Method findById() called with id:" + id));
        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
        auditService.save(auditService.generateAudit("Method deleteById() called with id:" + id));
    }

    @Transactional
    public UserDto update(UserDto user) {
        UserDto userDto = userDao.update(user);
        auditService.save(auditService.generateAudit("Method update() called with user:" + userDto));
        return userDto;
    }
}
