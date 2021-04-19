package by.dzikovskiy.usermicroservice.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("host.url")
@Getter
@Setter
@Component
public class HostProperties {
    private String postgresMicroserviceHost;
    private String mongoDbMicroserviceHost;
}
