package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.HostProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserPhotoRequestService {
    private final HostProperties hostProperties;
    private final String mongoDbHost = "http://localhost:8082/mongo";


}
