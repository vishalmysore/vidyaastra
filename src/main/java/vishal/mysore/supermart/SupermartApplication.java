package vishal.mysore.supermart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = {
    "vishal.mysore.supermart.config",
    "vishal.mysore.supermart.repository",
    "vishal.mysore.supermart.service",
    "vishal.mysore.supermart.model",
    "vishal.mysore.supermart.util"
})
@EnableNeo4jRepositories(basePackages = "vishal.mysore.supermart.repository")
public class SupermartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupermartApplication.class, args);
    }
}
