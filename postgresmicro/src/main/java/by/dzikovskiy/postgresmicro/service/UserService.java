package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.entity.UserDto;
import by.dzikovskiy.postgresmicro.entity.Visa;
import by.dzikovskiy.postgresmicro.mapper.UserDtoMapper;
import by.dzikovskiy.postgresmicro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserDto save(UserDto user) {
        User savedUser = userRepository.save(userDtoMapper.userDtoToUser(user));
        for (Visa t : savedUser.getVisas()) {
            t.setUser(savedUser);
        }

        savedUser = userRepository.save(savedUser);

        return userDtoMapper.userToUserDto(savedUser);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(userDtoMapper::userToUserDto);
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
