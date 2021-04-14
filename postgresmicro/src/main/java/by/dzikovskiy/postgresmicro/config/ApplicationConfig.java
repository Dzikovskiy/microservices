package by.dzikovskiy.postgresmicro.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.liquibase.change-log}")
    private String liquibaseChangelog;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(liquibaseChangelog);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
