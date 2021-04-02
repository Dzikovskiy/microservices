package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /*TODO fix exception while deserialized with photo field
     * */
    public User getUserFromJson(String json) throws JsonProcessingException {
        User user;
        ObjectMapper objectMapper = new ObjectMapper();
        //  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,true);
        user = objectMapper.readValue(json, User.class);


        return user;
    }


}
