package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Service;

@Repository
public interface ServiceRepository extends Neo4jRepository<Service, Long> {
    Service findByName(String name);
}
