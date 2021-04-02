package by.dzikovskiy.mongomicro.entity;


import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@Data
public class UserPhoto {

    private Long userId;
    private Binary image;
}