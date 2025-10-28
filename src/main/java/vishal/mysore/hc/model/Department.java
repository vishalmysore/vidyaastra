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
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "SPECIALIZES_IN", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> specializedConditions = new HashSet<>();

    @Relationship(type = "PERFORMS", direction = Relationship.Direction.OUTGOING)
    private Set<Procedure> procedures = new HashSet<>();

    @Relationship(type = "PROVIDES", direction = Relationship.Direction.OUTGOING)
    private Set<Treatment> treatments = new HashSet<>();
}
