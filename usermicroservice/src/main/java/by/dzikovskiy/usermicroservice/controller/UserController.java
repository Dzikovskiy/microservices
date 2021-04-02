package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.User;
import by.dzikovskiy.usermicroservice.service.UserProfileRequestService;
import by.dzikovskiy.usermicroservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProfileRequestService profileRequestService;

    @PostMapping(value = "/users", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> createUser(@RequestPart("user") String user, @RequestPart("file") MultipartFile file) {
        User parsedUser;

        try {
            parsedUser = userService.getUserFromJson(user);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        parsedUser = profileRequestService.create(parsedUser).block();

        //request sending to micro-s and kafka

        URI location = URI.create(String.format("/api/users/%s", parsedUser.getId()));
        return ResponseEntity.created(location).body(parsedUser);
    }

    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable Long id) {
        return profileRequestService.get(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long id) {
        return ResponseEntity.ok().body(new byte[]{0x00, 0x2b, 0x30});
    }

}
