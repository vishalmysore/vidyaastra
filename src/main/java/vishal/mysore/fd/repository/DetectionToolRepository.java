package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.DetectionTool;

@Repository
public interface DetectionToolRepository extends Neo4jRepository<DetectionTool, Long> {
    DetectionTool findByName(String name);
}
