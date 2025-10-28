package vishal.mysore.yoga.repository;

import vishal.mysore.yoga.model.YogaBenefit;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogaBenefitRepository extends Neo4jRepository<YogaBenefit, Long> {
    YogaBenefit findByName(String name);
}
