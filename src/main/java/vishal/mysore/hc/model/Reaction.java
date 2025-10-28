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
public class Reaction {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String severity;

    @Relationship(type = "MAY_CAUSE", direction = Relationship.Direction.INCOMING)
    private Set<Medicine> causingMedicines = new HashSet<>();
}
