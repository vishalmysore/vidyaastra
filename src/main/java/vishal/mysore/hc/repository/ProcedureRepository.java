package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Procedure;

@Repository
public interface ProcedureRepository extends Neo4jRepository<Procedure, Long> {
    Procedure findByName(String name);
}
