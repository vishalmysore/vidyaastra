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
public class Diagnosis {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String type;

    @Relationship(type = "IDENTIFIES", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> identifiedConditions = new HashSet<>();

    @Relationship(type = "USES", direction = Relationship.Direction.OUTGOING)
    private Set<Procedure> procedures = new HashSet<>();
}
