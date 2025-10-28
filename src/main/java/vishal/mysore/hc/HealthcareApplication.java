package vishal.mysore.hc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = {
    "vishal.mysore.hc.config",
    "vishal.mysore.hc.repository",
    "vishal.mysore.hc.service",
    "vishal.mysore.hc.model",
    "vishal.mysore.hc.util"
})
@EnableNeo4jRepositories(basePackages = "vishal.mysore.hc.repository")
public class HealthcareApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthcareApplication.class, args);
    }
}
