package ua.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ua.com.smida.model.Share;
import ua.com.smida.service.ShareService;

@SpringBootApplication
@EnableJpaAuditing
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

    @Bean
    CommandLineRunner write(ShareService shareService) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Share>> typeReference = new TypeReference<List<Share>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/insert.json");
            try {
                List<Share> shares = mapper.readValue(inputStream, typeReference);
                shares.forEach(shareService::create);
                System.out.println("share saved, count - " + shares.size());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        };
    }
}
