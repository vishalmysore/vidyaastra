package vishal.mysore.supermart.model;

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
public class Service {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "SERVES", direction = Relationship.Direction.OUTGOING)
    private Set<Customer> customers = new HashSet<>();

    @Relationship(type = "PART_OF", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();

    @Relationship(type = "LOCATED_NEAR", direction = Relationship.Direction.OUTGOING)
    private Set<Location> locations = new HashSet<>();
}
