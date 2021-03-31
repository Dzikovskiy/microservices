package by.dzikovskiy.mongomicro.service;

import by.dzikovskiy.mongomicro.entity.UserPhoto;
import by.dzikovskiy.mongomicro.repository.UserPhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserPhotoService {

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    public String savePhoto(Long userId, MultipartFile file) throws IOException {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(userId);
        userPhoto.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

        userPhoto = userPhotoRepository.insert(userPhoto);
        return userPhoto.getId();
    }

    public Optional<UserPhoto> getPhoto(Long userId) {
        return userPhotoRepository.findFirstByUserId(userId);
    }
}
