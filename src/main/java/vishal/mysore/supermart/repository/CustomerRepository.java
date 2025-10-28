package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Customer;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer, Long> {
    Customer findByName(String name);
}
