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
public class Supplier {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "SUPPLIES_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();

    @Relationship(type = "PARTNERS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Brand> brands = new HashSet<>();
}
