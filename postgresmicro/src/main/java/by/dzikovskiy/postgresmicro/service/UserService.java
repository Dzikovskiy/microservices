package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.UserPageResponse;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserResponse save(UserDto user);

    Optional<UserDto> findById(Long id);

    UserPageResponse findAll(Pageable page);

    void deleteById(Long id);

    UserDto update(UserDto user);
}
