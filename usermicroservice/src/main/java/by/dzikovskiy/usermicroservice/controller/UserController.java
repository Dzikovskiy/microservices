package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.User;
import by.dzikovskiy.usermicroservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> createUser(@RequestPart("user") String user, @RequestPart("file") MultipartFile file) {
        User parsedUser;

        try {
            parsedUser = userService.getUserFromJson(user, file);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //request sending to micro-s and kafka

        URI location = URI.create(String.format("/api/users/%s", parsedUser.getId()));
        return ResponseEntity.created(location).body(parsedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok().body(new User());
    }

    @GetMapping("/users/{id}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long id) {
        return ResponseEntity.ok().body(new byte[]{0x00, 0x2b, 0x30});
    }

}
