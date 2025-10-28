package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Treatment;

@Repository
public interface TreatmentRepository extends Neo4jRepository<Treatment, Long> {
    Treatment findByName(String name);
}
