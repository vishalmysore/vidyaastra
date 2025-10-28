package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Brand;

@Repository
public interface BrandRepository extends Neo4jRepository<Brand, Long> {
    Brand findByName(String name);
}
