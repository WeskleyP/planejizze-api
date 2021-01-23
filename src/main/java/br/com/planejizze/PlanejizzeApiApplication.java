package br.com.planejizze;

import br.com.planejizze.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class PlanejizzeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanejizzeApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
