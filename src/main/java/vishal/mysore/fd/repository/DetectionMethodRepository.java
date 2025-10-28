package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.DetectionMethod;

@Repository
public interface DetectionMethodRepository extends Neo4jRepository<DetectionMethod, Long> {
    DetectionMethod findByName(String name);
}
