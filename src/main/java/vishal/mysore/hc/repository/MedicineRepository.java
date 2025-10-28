package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Medicine;

@Repository
public interface MedicineRepository extends Neo4jRepository<Medicine, Long> {
    Medicine findByName(String name);
}
