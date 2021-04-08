package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.User;
import by.dzikovskiy.usermicroservice.service.UserPhotoRequestService;
import by.dzikovskiy.usermicroservice.service.UserProfileRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserProfileRequestService profileRequestService;
    private final UserPhotoRequestService photoRequestService;

    @PostMapping("/users")
    public ResponseEntity<User> createUserProfile(@RequestBody final User user) {
        Optional<User> optionalUser = profileRequestService.create(user);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        //request sending to micro-s and kafka

        URI location = URI.create(String.format("/api/users/%s", optionalUser.get().getId()));
        return ResponseEntity.created(location).body(optionalUser.get());
    }

    @PostMapping(value = "/users/{id}/photo")
    public ResponseEntity<HttpStatus> saveUserPhoto(@PathVariable final Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Optional<User> optionalUser = profileRequestService.get(id);
        log.debug("Method saveUserPhoto() called with id: " + id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        HttpStatus status = photoRequestService.save(file, id);

        return new ResponseEntity<>(status);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable final Long id) {
        Optional<User> optionalUser = profileRequestService.get(id);

        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/users/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable final Long id) {
        Optional<byte[]> optionalPhoto = photoRequestService.get(id);

        return optionalPhoto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@RequestBody final User user) {
        Optional<User> optionalUser = profileRequestService.update(user);

        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/users/{id}/photo")
    public ResponseEntity<HttpStatus> updateUserPhoto(@PathVariable final Long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.debug("Method updateUserPhoto() called with id: " + id);

        HttpStatus status = photoRequestService.update(file, id);

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable final Long id) {
        photoRequestService.delete(id);
        profileRequestService.delete(id);
    }
}
