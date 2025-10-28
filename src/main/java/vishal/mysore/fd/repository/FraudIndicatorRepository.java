package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.FraudIndicator;

@Repository
public interface FraudIndicatorRepository extends Neo4jRepository<FraudIndicator, Long> {
    FraudIndicator findByName(String name);
}
