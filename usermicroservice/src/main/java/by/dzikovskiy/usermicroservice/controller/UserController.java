package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.User;
import by.dzikovskiy.usermicroservice.properties.KafkaConstants;
import by.dzikovskiy.usermicroservice.service.UserPhotoRequestService;
import by.dzikovskiy.usermicroservice.service.UserProfileRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserProfileRequestService profileRequestService;
    private final UserPhotoRequestService photoRequestService;
    private final KafkaTemplate<String, User> kafkaTemplate;

    @PostMapping("/users")
    public ResponseEntity<User> createUserProfile(@RequestBody final User user) {
        return profileRequestService.create(user)
                .map(_user -> {
                    //saving to kafka microservice
                    kafkaTemplate.send(KafkaConstants.USERS_TOPIC, _user);
                    return ResponseEntity
                            .created(URI.create(String.format("/api/users/%s", _user.getId())))
                            .body(_user);
                })
                .orElseGet(() -> ResponseEntity.badRequest()
                        .build());
    }

    @PostMapping(value = "/users/{id}/photo")
    public ResponseEntity<HttpStatus> saveUserPhoto(@PathVariable final Long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.debug("Method saveUserPhoto() called with id: " + id);
        return profileRequestService.get(id).map(user -> {
            HttpStatus status;
            try {
                status = photoRequestService.save(file, id);
            } catch (IOException e) {
                log.info(e.getMessage());
                return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
            }

            log.debug("Photo saved to database with user id: " + id);
            return new ResponseEntity<HttpStatus>(status);
        }).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable final Long id) {
        log.debug("Method getUser() called with id: " + id);
        return profileRequestService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/users/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable final Long id) {
        log.debug("Method getUserPhoto() called with id: " + id);
        return photoRequestService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@RequestBody final User user) {
        log.debug("Method updateUserProfile() called with user: " + user);
        return profileRequestService.update(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/users/{id}/photo")
    public ResponseEntity<HttpStatus> updateUserPhoto(@PathVariable final Long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.debug("Method updateUserPhoto() called with id: " + id);
        return new ResponseEntity<>(photoRequestService.update(file, id));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable final Long id) {
        log.debug("Method deleteUserById() called with id: " + id);
        photoRequestService.delete(id);
        profileRequestService.delete(id);
    }
}
