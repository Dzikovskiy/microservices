package by.dzikovskiy.postgresmicro.mapper;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
