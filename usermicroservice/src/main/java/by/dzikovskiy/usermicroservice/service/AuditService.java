package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.AuditDto;
import by.dzikovskiy.usermicroservice.entity.HostProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AuditService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final HostProperties hostProperties;
    private final RestTemplate restTemplate;
    private final String postgresHost;

    @Autowired
    public AuditService(HostProperties hostProperties, RestTemplate restTemplate) {
        this.hostProperties = hostProperties;
        this.restTemplate = restTemplate;
        this.postgresHost = this.hostProperties.getPostgresMicroserviceHost();
    }

    public void save(final String message) {
        try {
            restTemplate.postForEntity(postgresHost + "/audit", new AuditDto(generateAudit(message)), String.class);
        } catch (HttpClientErrorException e) {
        }
    }

    public String generateAudit(final String message) {
        Date date = new Date();
        return message + " ; " + dateFormat.format(date);
    }
}
