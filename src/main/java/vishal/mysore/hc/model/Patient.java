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
public class Patient {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "DIAGNOSED_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> conditions = new HashSet<>();

    @Relationship(type = "PRESCRIBED", direction = Relationship.Direction.OUTGOING)
    private Set<Medicine> prescriptions = new HashSet<>();

    @Relationship(type = "PRESENTS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Symptom> symptoms = new HashSet<>();

    @Relationship(type = "REFERRED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();
}
