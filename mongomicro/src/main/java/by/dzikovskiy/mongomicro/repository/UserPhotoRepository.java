package by.dzikovskiy.mongomicro.repository;

import by.dzikovskiy.mongomicro.entity.UserPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPhotoRepository extends MongoRepository<UserPhoto, String> {
    Optional<UserPhoto> findFirstByUserId(Long id);
}
