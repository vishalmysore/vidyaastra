package vishal.mysore.supermart.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import vishal.mysore.hc.model.Department;

import java.util.HashSet;
import java.util.Set;

@Node
@Getter
@Setter
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "SHOPS_IN", direction = Relationship.Direction.OUTGOING)
    private Set<Department> departments = new HashSet<>();

    @Relationship(type = "PREFERS", direction = Relationship.Direction.OUTGOING)
    private Set<Category> preferences = new HashSet<>();

    @Relationship(type = "PURCHASES", direction = Relationship.Direction.OUTGOING)
    private Set<Product> purchases = new HashSet<>();

    @Relationship(type = "ENROLLED_IN", direction = Relationship.Direction.OUTGOING)
    private Set<Promotion> promotions = new HashSet<>();

    @Relationship(type = "USES", direction = Relationship.Direction.OUTGOING)
    private Set<Service> services = new HashSet<>();

    @Relationship(type = "PAYS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Payment> paymentMethods = new HashSet<>();
}
