package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.entity.UserDto;
import by.dzikovskiy.postgresmicro.entity.Visa;
import by.dzikovskiy.postgresmicro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto save(UserDto user) {
        User savedUser = userRepository.save(UserDtoMapper.USER_MAPPER.userDtoToUser(user));
        for (Visa t : savedUser.getVisas()) {
            t.setUser(savedUser);
        }

        savedUser = userRepository.save(savedUser);

        return UserDtoMapper.USER_MAPPER.userToUserDto(savedUser);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserDtoMapper.USER_MAPPER::userToUserDto);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto update(UserDto user) {
        Optional<UserDto> optionalUser = findById(user.getId());

        optionalUser.get().setName(user.getName());
        optionalUser.get().setVisas(user.getVisas());

        return save(optionalUser.get());
    }
}
