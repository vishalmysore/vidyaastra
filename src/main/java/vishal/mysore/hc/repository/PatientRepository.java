package vishal.mysore.hc.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import vishal.mysore.hc.model.Patient;

@Repository
public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    Patient findByName(String name);
}
