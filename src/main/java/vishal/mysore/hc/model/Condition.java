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
public class Condition {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    @Relationship(type = "CAUSES", direction = Relationship.Direction.OUTGOING)
    private Set<Symptom> symptoms = new HashSet<>();

    @Relationship(type = "TREATED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Medicine> medicines = new HashSet<>();

    @Relationship(type = "MANAGES", direction = Relationship.Direction.INCOMING)
    private Set<Treatment> treatments = new HashSet<>();

    @Relationship(type = "DIAGNOSED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Procedure> procedures = new HashSet<>();
}
