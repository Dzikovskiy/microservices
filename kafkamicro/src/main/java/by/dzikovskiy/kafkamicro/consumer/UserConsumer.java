package by.dzikovskiy.kafkamicro.consumer;

import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserConsumer {
    private final UserServiceImpl userService;

    @KafkaListener(topics = "USERS", groupId = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeUser(UserDto user) {
        log.debug("Method consumeUser() called with user: " + user);
        userService.save(user);
    }
}
