package by.dzikovskiy.kafkamicro.mapper;


import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
