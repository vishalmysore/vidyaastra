package vishal.mysore.cricket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "vishal.mysore.cricket.config",
    "vishal.mysore.cricket.repository",
    "vishal.mysore.cricket.service",
    "vishal.mysore.cricket.model",
    "vishal.mysore.cricket.util"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}