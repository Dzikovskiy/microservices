package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

/*TODO fix exception while deserialized with photo field
* */
    public User getUserFromJson(String json, MultipartFile file) throws JsonProcessingException {
        User user;
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(json, User.class);
        user.setPhoto(file);

        return user;
    }
}
