package vishal.mysore.fd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FraudDetectionApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FraudDetectionApplication.class, args);
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
