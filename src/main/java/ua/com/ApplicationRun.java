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
import ua.com.smida.model.user.User;
import ua.com.smida.service.ShareService;
import ua.com.smida.service.userservice.UserService;

@SpringBootApplication
@EnableJpaAuditing
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

    @Bean
    CommandLineRunner write(ShareService shareService, UserService userService) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Share>> typeShape = new TypeReference<List<Share>>() {
            };
            InputStream inputShape = TypeReference.class.getResourceAsStream("/json/insert.json");
            try {
                List<Share> shares = mapper.readValue(inputShape, typeShape);
                shares.forEach(shareService::create);
                System.out.println("share saved, count - " + shares.size());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            TypeReference<List<User>> typeUser = new TypeReference<List<User>>() {
            };
            InputStream inputUser = TypeReference.class
                .getResourceAsStream("/json/insertUserJson.json");
            try {
                List<User> candidate = mapper.readValue(inputUser, typeUser);
                candidate.forEach(userService::initJson);
                System.out.println("user saved, count - " + candidate.size());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        };
    }
}
