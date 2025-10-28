package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Reaction;

@Repository
public interface ReactionRepository extends Neo4jRepository<Reaction, Long> {
    Reaction findByName(String name);
}
