package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Category;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    Category findByName(String name);
}
