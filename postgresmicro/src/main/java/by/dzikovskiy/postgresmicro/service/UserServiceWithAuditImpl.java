package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceWithAuditImpl implements UserService {
    private final UserServiceImpl userServiceImpl;
    private final AuditService auditService;

    @Override
    @Transactional
    public UserDto save(UserDto user) {
        UserDto userDto = userServiceImpl.save(user);
        auditService.save(auditService.generateAudit("Method save() called with user:" + userDto));
        return userDto;
    }

    @Override
    @Transactional
    public Optional<UserDto> findById(Long id) {
        Optional<UserDto> user = userServiceImpl.findById(id);
        auditService.save(auditService.generateAudit("Method findById() called with id:" + id));
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userServiceImpl.deleteById(id);
        auditService.save(auditService.generateAudit("Method deleteById() called with id:" + id));
    }

    @Override
    public UserDto update(UserDto user) {
        UserDto userDto = userServiceImpl.update(user);
        auditService.save(auditService.generateAudit("Method update() called with user:" + userDto));
        return userDto;
    }
}
