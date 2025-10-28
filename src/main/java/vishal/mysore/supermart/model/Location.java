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
public class Location {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "ACCEPTS", direction = Relationship.Direction.OUTGOING)
    private Set<Payment> acceptedPayments = new HashSet<>();

    @Relationship(type = "STORES_INVENTORY", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();

    @Relationship(type = "MANAGES", direction = Relationship.Direction.OUTGOING)
    private Set<Promotion> promotions = new HashSet<>();
}
