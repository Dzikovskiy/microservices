package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileRequestService {
    private final String postgresHost = "http://localhost:8083/postgres";

    private final WebClient webClient;

    public Mono<User> create(User user) {
        return webClient.post()
                .uri(postgresHost + "/users")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> get(Long id) {
        return webClient.get()
                .uri(postgresHost + "/users/" + id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.empty())
                .bodyToMono(User.class);
    }
}
