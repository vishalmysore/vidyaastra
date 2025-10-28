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
public class Promotion {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "DISCOUNTS", direction = Relationship.Direction.OUTGOING)
    private Set<Product> products = new HashSet<>();

    @Relationship(type = "EARNED_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Payment> payments = new HashSet<>();

    @Relationship(type = "APPLIES_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();
}
