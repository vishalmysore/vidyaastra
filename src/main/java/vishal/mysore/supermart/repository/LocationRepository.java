package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Location;

@Repository
public interface LocationRepository extends Neo4jRepository<Location, Long> {
    Location findByName(String name);
}
