package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.PreventionMethod;

@Repository
public interface PreventionMethodRepository extends Neo4jRepository<PreventionMethod, Long> {
    PreventionMethod findByName(String name);
}
