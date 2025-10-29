package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.StoreService;

@Repository
public interface StoreServiceRepository extends Neo4jRepository<StoreService, Long> {
    StoreService findByName(String name);
}
