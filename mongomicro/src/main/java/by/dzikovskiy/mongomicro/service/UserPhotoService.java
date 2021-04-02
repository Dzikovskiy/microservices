package by.dzikovskiy.mongomicro.service;

import by.dzikovskiy.mongomicro.entity.UserPhoto;
import by.dzikovskiy.mongomicro.repository.UserPhotoRepository;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPhotoService {
    private final UserPhotoRepository userPhotoRepository;

    public long savePhoto(Long userId, MultipartFile file) throws IOException {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(userId);
        userPhoto.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

        userPhoto = userPhotoRepository.insert(userPhoto);
        return userPhoto.getUserId();
    }

    public Optional<UserPhoto> getPhoto(Long userId) {
        return userPhotoRepository.findFirstByUserId(userId);
    }
}
