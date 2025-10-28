package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Promotion;

@Repository
public interface PromotionRepository extends Neo4jRepository<Promotion, Long> {
    Promotion findByName(String name);
}
