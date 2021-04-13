package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.UserDto;

import java.util.Optional;

public interface UserService {
    UserDto save(UserDto user);

    Optional<UserDto> findById(Long id);

    void deleteById(Long id);

    UserDto update(UserDto user);
}
