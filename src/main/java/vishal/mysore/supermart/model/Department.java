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
public class Department {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "STOCKS", direction = Relationship.Direction.OUTGOING)
    private Set<Product> products = new HashSet<>();

    @Relationship(type = "SELLS", direction = Relationship.Direction.OUTGOING)
    private Set<Category> categories = new HashSet<>();

    @Relationship(type = "SUPPLIED_TO", direction = Relationship.Direction.INCOMING)
    private Set<Supplier> suppliers = new HashSet<>();

    @Relationship(type = "STORES_INVENTORY", direction = Relationship.Direction.INCOMING)
    private Set<Location> storageLocations = new HashSet<>();
}
