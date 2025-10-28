package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Diagnosis;

@Repository
public interface DiagnosisRepository extends Neo4jRepository<Diagnosis, Long> {
    Diagnosis findByName(String name);
}
