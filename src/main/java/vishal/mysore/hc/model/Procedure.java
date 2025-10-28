package vishal.mysore.hc.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Getter
@Setter
public class Procedure {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    @Relationship(type = "DIAGNOSES", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> diagnosedConditions = new HashSet<>();

    @Relationship(type = "USED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Diagnosis> usedInDiagnosis = new HashSet<>();

    @Relationship(type = "PERFORMED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Department> performingDepartments = new HashSet<>();
}
