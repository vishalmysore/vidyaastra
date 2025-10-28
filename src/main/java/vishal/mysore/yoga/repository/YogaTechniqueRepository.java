package vishal.mysore.yoga.repository;

import vishal.mysore.yoga.model.YogaTechnique;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogaTechniqueRepository extends Neo4jRepository<YogaTechnique, Long> {
    YogaTechnique findByName(String name);
}
