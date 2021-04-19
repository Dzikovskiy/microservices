package by.dzikovskiy.kafkamicro.consumer;

import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserConsumer {
    private static final String USERS_TOPIC = "USERS";
    private final UserServiceImpl userService;

    @Autowired
    public UserConsumer(UserServiceImpl userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = USERS_TOPIC, groupId = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeUser(UserDto user) {
        log.debug("Method consumeUser() called with user: " + user);
        userService.save(user);
    }
}
