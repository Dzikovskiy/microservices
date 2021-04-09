package by.dzikovskiy.postgresmicro.controller;

import by.dzikovskiy.postgresmicro.entity.UserDto;
import by.dzikovskiy.postgresmicro.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/postgres")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto user) {
        log.debug("Method createUser() called with user:" + user);
        UserDto result = userService.save(user);
        URI location = URI.create(String.format("/postgres/users/%s", result.getId()));

        log.debug("Response with location: " + location + " and user: " + result);
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        log.debug("Method getUser() called with id: " + id);

        return userService.findById(id).map(user -> {
            log.debug("Response with OK and user: " + user);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> {
            log.debug("Response with NOT_FOUND. User with the given id is not in the repository");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody final UserDto user) {
        log.debug("Method updateUser() called with id: " + user.getId());

        return userService.findById(user.getId()).map(userDto -> {
            userService.update(user);
            log.debug("Response with OK and user: " + user);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> {
            log.debug("Response with HttpStatus.NOT_FOUND. User with given id not in the repository");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable final Long id) {
        log.debug("Method deleteUser() called with id: " + id);

        return userService.findById(id)
                .map(user -> {
                    userService.deleteById(id);
                    log.debug("Response with NO_CONTENT. User deleted successfully");
                    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
                }).orElseGet(() -> {
                    log.debug("Response with NOT_FOUND. User with the given id is not in the repository");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }
}
