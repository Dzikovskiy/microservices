package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.entity.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDtoMapper USER_MAPPER = Mappers.getMapper(UserDtoMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
