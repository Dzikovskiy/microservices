package by.dzikovskiy.kafkamicro.service;


import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;

public interface UserService {
    User save(UserDto user);
}
