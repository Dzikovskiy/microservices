package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserFromJson(String json) throws JsonProcessingException {
        User user;
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(json, User.class);

        return user;
    }
}
