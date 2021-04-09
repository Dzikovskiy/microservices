package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.HostProperties;
import by.dzikovskiy.usermicroservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserProfileRequestService {
    private final HostProperties hostProperties;
    private final RestTemplate restTemplate;
    private final String postgresHost;

    @Autowired
    public UserProfileRequestService(HostProperties hostProperties, RestTemplate restTemplate) {
        this.hostProperties = hostProperties;
        this.restTemplate = restTemplate;
        this.postgresHost = this.hostProperties.getPostgresMicroserviceHost();
    }

    public Optional<User> create(User user) {
        ResponseEntity<User> responseUser;
        try {
            responseUser = restTemplate.postForEntity(postgresHost + "/users", user, User.class);
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }

        return Optional.of(responseUser.getBody());
    }

    public Optional<User> get(Long id) {
        ResponseEntity<User> responseUser;
        try {
            responseUser = restTemplate.getForEntity(postgresHost + "/users/" + id, User.class);
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }

        return Optional.of(responseUser.getBody());
    }

    public Optional<User> update(User user) {
        ResponseEntity<User> responseUser;
        try {
            responseUser = restTemplate.exchange(postgresHost + "/users/{id}",
                    HttpMethod.PUT,
                    new HttpEntity<>(user),
                    User.class,
                    Long.toString(user.getId()));
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }

        return Optional.of(responseUser.getBody());
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(postgresHost + "/users/" + id);
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
        }
    }
}
