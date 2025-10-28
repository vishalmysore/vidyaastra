package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.FraudType;

@Repository
public interface FraudTypeRepository extends Neo4jRepository<FraudType, Long> {
    FraudType findByName(String name);
}
