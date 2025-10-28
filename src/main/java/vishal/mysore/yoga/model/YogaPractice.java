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
public class YogaPractice {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    @Relationship(type = "REQUIRES")
    private  Set<YogaEquipment> equipment = new HashSet<>();

    @Relationship(type = "TECHNIQUE")
    private  Set<YogaTechnique> techniques = new HashSet<>();

    @Relationship(type = "IMPROVES")
    private  Set<YogaBenefit> benefits = new HashSet<>();

    public YogaPractice() {
    }

    public YogaPractice(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
