package vishal.mysore.yoga.repository;

import vishal.mysore.yoga.model.YogaPractice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogaPracticeRepository extends Neo4jRepository<YogaPractice, Long> {
    YogaPractice findByName(String name);
}
