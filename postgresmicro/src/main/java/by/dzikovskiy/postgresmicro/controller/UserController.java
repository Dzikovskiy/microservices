package by.dzikovskiy.postgresmicro.controller;

import by.dzikovskiy.postgresmicro.entity.User;
import by.dzikovskiy.postgresmicro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/postgres")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User result = userService.save(user);
        URI location = URI.create(String.format("/postgres/users/%s", result.getId()));

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        return optionalUser.map(user ->
                ResponseEntity.ok().body(user))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
