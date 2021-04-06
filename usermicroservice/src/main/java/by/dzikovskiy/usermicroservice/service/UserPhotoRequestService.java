package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.HostProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class UserPhotoRequestService {
    private final HostProperties hostProperties;
    private final WebClient webClient;
    private final String mongoDbHost;

    @Autowired
    public UserPhotoRequestService(HostProperties hostProperties, WebClient webClient) {
        this.hostProperties = hostProperties;
        this.webClient = webClient;
        this.mongoDbHost = this.hostProperties.getMongoDbMicroserviceHost();
    }

    public void save(MultipartFile file, Long userId) throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("userPhoto", file.getBytes()).header("Content-Disposition", "form-data; name=userPhoto; filename=" + file.getName());
        bodyBuilder.part("userId", userId);

        webClient.post()
                .uri(mongoDbHost + "/users/photo")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        status -> Mono.empty())
                .bodyToMono(Mono.class).block();
    }

    public Mono<byte[]> get(Long userId) {
        return webClient.get()
                .uri(mongoDbHost + "/users/photo/" + userId)
                .accept(MediaType.IMAGE_JPEG)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.empty())
                .bodyToMono(byte[].class);
    }

    public Mono<HttpStatus> delete(Long id){
        return webClient.delete()
                .uri(mongoDbHost+"/users/photo/"+id)
                .exchange()
                .map(ClientResponse::statusCode).defaultIfEmpty(HttpStatus.NOT_FOUND);
    }

}
