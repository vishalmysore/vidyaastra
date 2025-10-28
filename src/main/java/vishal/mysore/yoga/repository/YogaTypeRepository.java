package vishal.mysore.yoga.repository;

import vishal.mysore.yoga.model.YogaType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogaTypeRepository extends Neo4jRepository<YogaType, Long> {
    YogaType findByName(String name);
}
