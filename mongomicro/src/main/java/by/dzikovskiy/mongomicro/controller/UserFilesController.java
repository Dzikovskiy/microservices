package by.dzikovskiy.mongomicro.controller;

import by.dzikovskiy.mongomicro.entity.UserPhoto;
import by.dzikovskiy.mongomicro.service.UserPhotoService;
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
public class UserFilesController {

    @Autowired
    private UserPhotoService userPhotoService;

    @PostMapping("/users/photo")
    public ResponseEntity<?> saveUserPhoto(@RequestParam("userPhoto") MultipartFile userPhoto,
                                           @RequestParam("userId") Long id) {
        URI location = URI.create(String.format("/mongo/users/photos/%s", id));

        try {
            userPhotoService.savePhoto(id, userPhoto);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long id) {
        Optional<UserPhoto> optionalUserPhoto = userPhotoService.getPhoto(id);
        return optionalUserPhoto.map(userPhoto ->
                ResponseEntity.ok().body(userPhoto.getImage().getData()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
