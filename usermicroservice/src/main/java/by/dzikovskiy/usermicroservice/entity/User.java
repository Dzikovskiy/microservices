package by.dzikovskiy.usermicroservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class User {
    private long id;
    private String name;
    private MultipartFile photo;
}
