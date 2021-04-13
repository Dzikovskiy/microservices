package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.UserResponse;

import java.util.Optional;

public interface UserService {
    UserResponse save(UserDto user);

    Optional<UserDto> findById(Long id);

    void deleteById(Long id);

    UserDto update(UserDto user);
}
