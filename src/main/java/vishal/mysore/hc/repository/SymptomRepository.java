package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Symptom;

@Repository
public interface SymptomRepository extends Neo4jRepository<Symptom, Long> {
    Symptom findByName(String name);
}
