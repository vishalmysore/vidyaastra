package vishal.mysore.supermart.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.supermart.model.Department;

@Repository
public interface DepartmentRepository extends Neo4jRepository<Department, Long> {
    Department findByName(String name);
}
