package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Condition;

@Repository
public interface ConditionRepository extends Neo4jRepository<Condition, Long> {
    Condition findByName(String name);
}
