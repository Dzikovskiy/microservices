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

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserProfileRequestService profileRequestService;
    private final UserPhotoRequestService photoRequestService;

    @PostMapping("/users")
    public ResponseEntity<User> createUserProfile(@RequestBody final User user) {
        return profileRequestService.create(user)
                .map(_user -> ResponseEntity
                        .created(URI.create(String.format("/api/users/%s", _user.getId())))
                        .body(_user))
                .orElse(ResponseEntity.badRequest()
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
                log.debug(e.getMessage());
                return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<HttpStatus>(status);
        }).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable final Long id) {
        return profileRequestService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/users/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable final Long id) {
        return photoRequestService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@RequestBody final User user) {
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
        photoRequestService.delete(id);
        profileRequestService.delete(id);
    }
}
