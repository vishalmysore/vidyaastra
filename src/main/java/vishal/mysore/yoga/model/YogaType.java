package vishal.mysore.yoga.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node
public class YogaType {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    @Relationship(type = "INCLUDES")
    private  Set<YogaPractice> practices = new HashSet<>();

    @Relationship(type = "PROVIDES")
    private  Set<YogaBenefit> benefits = new HashSet<>();

    @Relationship(type = "MODERN_FORM", direction = Relationship.Direction.OUTGOING)
    private Set<YogaType> modernStyles = new HashSet<>();

    @Relationship(type = "MODERN_FORM", direction = Relationship.Direction.INCOMING)
    private Set<YogaType> parentStyles = new HashSet<>();

    public YogaType() {
    }

    public YogaType(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
