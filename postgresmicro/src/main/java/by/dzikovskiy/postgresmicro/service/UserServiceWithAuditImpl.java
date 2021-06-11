package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.UserWithAuditDao;
import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.entity.UserPageResponse;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import by.dzikovskiy.postgresmicro.mapper.UserDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceWithAuditImpl implements UserService {
    private final UserWithAuditDao userWithAuditDao;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserResponse save(UserDto user) {
        return userWithAuditDao.save(user);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userWithAuditDao.findById(id);
    }

    @Override
    public UserPageResponse findAll(Pageable page) {
        Page<User> userPage = userWithAuditDao.findAll(page);
        UserPageResponse response = new UserPageResponse();

        response.setUsers(userPage.getContent().stream().map(userDtoMapper::userToUserDto).collect(Collectors.toList()));
        response.setCurrentPage(userPage.getNumber());
        response.setTotalItems(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());

        return response;
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
