package by.dzikovskiy.postgresmicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PostgresMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostgresMicroApplication.class, args);
    }


}
