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
public class Payment {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "PAYS_WITH", direction = Relationship.Direction.INCOMING)
    private Set<Customer> customers = new HashSet<>();

    @Relationship(type = "ACCEPTS", direction = Relationship.Direction.INCOMING)
    private Set<Location> locations = new HashSet<>();

    @Relationship(type = "EARNED_WITH", direction = Relationship.Direction.INCOMING)
    private Set<Promotion> promotions = new HashSet<>();
}
