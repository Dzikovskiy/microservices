package by.dzikovskiy.usermicroservice.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("host.url")
@Getter
@Setter
public class HostProperties {
    private String postgresMicroserviceHost;
    private String mongoDbMicroserviceHost;

}
