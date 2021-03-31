package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user,
                                           @RequestParam("userPhoto") MultipartFile userPhoto) {

        User result = new User();

        //request sending to micro-s

        URI location = URI.create(String.format("/api/users/%s", result.getId()));

        return ResponseEntity.created(location).body(result);
    }

}
