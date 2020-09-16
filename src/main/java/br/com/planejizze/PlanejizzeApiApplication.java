package br.com.planejizze;

import br.com.planejizze.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class PlanejizzeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanejizzeApiApplication.class, args);
    }

}
