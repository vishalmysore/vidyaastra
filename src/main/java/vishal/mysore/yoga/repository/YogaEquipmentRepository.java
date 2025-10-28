package vishal.mysore.yoga.repository;

import vishal.mysore.yoga.model.YogaEquipment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogaEquipmentRepository extends Neo4jRepository<YogaEquipment, Long> {
    YogaEquipment findByName(String name);
}
