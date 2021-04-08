package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.HostProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserPhotoRequestService {
    private final HostProperties hostProperties;
    private final RestTemplate restTemplate;
    private final String mongoDbHost;
    private static final String USER_URL = "/users/photo/";

    @Autowired
    public UserPhotoRequestService(HostProperties hostProperties, RestTemplate restTemplate) {
        this.hostProperties = hostProperties;
        this.restTemplate = restTemplate;
        this.mongoDbHost = this.hostProperties.getMongoDbMicroserviceHost();
    }

    public HttpStatus save(MultipartFile file, Long userId) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequestEntity(file);

        ResponseEntity<String> response = restTemplate
                .postForEntity(mongoDbHost + USER_URL + userId, requestEntity, String.class);

        return response.getStatusCode();
    }

    public Optional<byte[]> get(Long userId) {
        byte[] photo;

        try {
            photo = restTemplate.getForObject(mongoDbHost + USER_URL + userId, byte[].class);
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }

        return Optional.of(photo);
    }

    public HttpStatus update(MultipartFile file, Long userId) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequestEntity(file);

        ResponseEntity<Void> response;
        try {
            response = restTemplate.exchange(mongoDbHost + USER_URL + userId,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class);
        } catch (HttpClientErrorException e) {
            return HttpStatus.BAD_REQUEST;
        }

        return response.getStatusCode();
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(mongoDbHost + USER_URL + id);
        }catch (HttpClientErrorException e){
            log.error(e.getMessage());
        }
    }

    private HttpEntity<MultiValueMap<String, Object>> buildRequestEntity(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> photoHeaderMap = new LinkedMultiValueMap<>();
        photoHeaderMap.add("Content-disposition", "form-data; name=userPhoto; filename=" + file.getOriginalFilename());
        photoHeaderMap.add("Content-type", "image/jpeg");

        HttpEntity<byte[]> photo = new HttpEntity<>(file.getBytes(), photoHeaderMap);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("userPhoto", photo);

        return new HttpEntity<>(body, headers);
    }
}
