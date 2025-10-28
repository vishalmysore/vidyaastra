package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Product;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, Long> {
    Product findByName(String name);
}
