package by.dzikovskiy.mongomicro.controller;

import by.dzikovskiy.mongomicro.entity.UserPhoto;
import by.dzikovskiy.mongomicro.service.UserPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mongo")
@Slf4j
public class UserFilesController {

    @Autowired
    private UserPhotoService userPhotoService;

    @PostMapping("/users/photo")
    public ResponseEntity<HttpStatus> saveUserPhoto(@RequestParam("userPhoto") MultipartFile userPhoto,
                                                    @RequestParam("userId") final Long id) {
        URI location = URI.create(String.format("/mongo/users/photos/%s", id));
        log.debug("Method saveUserPhoto() called with id: " + id + " and userPhoto name: " + userPhoto.getName());

        try {
            userPhotoService.savePhoto(id, userPhoto);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.debug("Photo saved to database");
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable final Long id) {
        log.debug("Method getUserPhoto() called with id: " + id);
        Optional<UserPhoto> optionalUserPhoto = userPhotoService.getPhoto(id);
        return optionalUserPhoto
                .map(userPhoto -> {
                    log.debug("Photo found with given id: " + id);
                    return ResponseEntity.ok().body(userPhoto.getImage().getData());
                })
                .orElseGet(() -> {
                    log.debug("Photo not found with given id: " + id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("/users/photo/{id}")
    public ResponseEntity<HttpStatus> deletePhotoByUserId(@PathVariable final Long id) {
        log.debug("Method deletePhotoByUserId() called with id: " + id);
        Optional<UserPhoto> optionalUserPhoto = userPhotoService.getPhoto(id);

        return optionalUserPhoto
                .map(userPhoto -> {
                    log.debug("Photo deleted with given id: " + id);
                    userPhotoService.deletePhotoById(id);
                    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> {
                    log.debug("Photo not found with given id: " + id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/users/photo")
    public ResponseEntity<HttpStatus> updateUserPhoto(@RequestParam("userPhoto") MultipartFile userPhoto,
                                                      @RequestParam("userId") final Long id) {
        Optional<UserPhoto> optionalPhoto = userPhotoService.getPhoto(id);

        return optionalPhoto.map(photo -> {
            try {
                userPhotoService.update(id, userPhoto);
            } catch (IOException e) {
                log.error("Exception while photo saving" + e.getMessage());
                return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            log.debug("Response with OK. Photo with the given id has updated");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }).orElseGet(() -> {
            log.debug("Response with HttpStatus.NOT_FOUND. Photo with the given id is not in the repository");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });


    }
}
