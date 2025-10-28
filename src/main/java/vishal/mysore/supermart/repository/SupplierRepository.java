package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Supplier;

@Repository
public interface SupplierRepository extends Neo4jRepository<Supplier, Long> {
    Supplier findByName(String name);
}
