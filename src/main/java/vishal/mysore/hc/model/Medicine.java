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
public class Medicine {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "TREATS", direction = Relationship.Direction.OUTGOING)
    private Set<Condition> treatedConditions = new HashSet<>();

    @Relationship(type = "MAY_CAUSE", direction = Relationship.Direction.OUTGOING)
    private Set<Reaction> adverseReactions = new HashSet<>();

    @Relationship(type = "INCLUDED_IN", direction = Relationship.Direction.INCOMING)
    private Set<Treatment> treatments = new HashSet<>();
}
