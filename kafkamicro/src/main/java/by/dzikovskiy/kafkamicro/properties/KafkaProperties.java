package by.dzikovskiy.kafkamicro.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("kafka")
@Getter
@Setter
@Component
public class KafkaProperties {
    private String usersTopic;
    private String kafkaHost;
}
