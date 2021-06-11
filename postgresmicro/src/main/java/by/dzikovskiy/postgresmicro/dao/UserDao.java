package by.dzikovskiy.postgresmicro.dao;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import by.dzikovskiy.postgresmicro.mapper.UserDtoMapper;
import by.dzikovskiy.postgresmicro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDao {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Transactional
    public UserResponse save(UserDto user) {
        User savedUser = userRepository.save(userDtoMapper.userDtoToUser(user));

        return new UserResponse(userDtoMapper.userToUserDto(savedUser), savedUser.getVisas().size());
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(userDtoMapper::userToUserDto);
    }

    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto update(UserDto user) {
        Optional<UserDto> optionalUser = findById(user.getId());
        if (optionalUser.isEmpty()) {
            return save(user).getUser();
        }

        optionalUser.get().setName(user.getName());
        optionalUser.get().setVisas(user.getVisas());

        return save(optionalUser.get()).getUser();
    }
}
