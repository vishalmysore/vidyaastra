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
public class Brand {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "SUPPLIES", direction = Relationship.Direction.OUTGOING)
    private Set<Product> products = new HashSet<>();

    @Relationship(type = "SPECIALIZES_IN", direction = Relationship.Direction.OUTGOING)
    private Set<Category> specialties = new HashSet<>();

    @Relationship(type = "PARTNERS_WITH", direction = Relationship.Direction.INCOMING)
    private Set<Supplier> suppliers = new HashSet<>();
}
