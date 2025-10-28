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
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Relationship(type = "CLASSIFIED_AS", direction = Relationship.Direction.OUTGOING)
    private Set<Category> categories = new HashSet<>();

    @Relationship(type = "SUPPLIED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Brand> brands = new HashSet<>();

    @Relationship(type = "DISCOUNTS", direction = Relationship.Direction.INCOMING)
    private Set<Promotion> promotions = new HashSet<>();

    @Relationship(type = "STOCKS", direction = Relationship.Direction.INCOMING)
    private Set<Department> departments = new HashSet<>();
}
