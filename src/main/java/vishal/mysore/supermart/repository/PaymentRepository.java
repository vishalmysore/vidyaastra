package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Payment;

@Repository
public interface PaymentRepository extends Neo4jRepository<Payment, Long> {
    Payment findByName(String name);
}
