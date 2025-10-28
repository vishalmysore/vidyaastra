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
public class Treatment {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "INCLUDES", direction = Relationship.Direction.OUTGOING)
    private Set<Medicine> medicines = new HashSet<>();

    @Relationship(type = "MANAGES", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> managedConditions = new HashSet<>();

    @Relationship(type = "PROVIDED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Department> departments = new HashSet<>();
}
