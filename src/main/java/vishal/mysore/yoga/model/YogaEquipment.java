package vishal.mysore.yoga.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node
public class YogaEquipment {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    public YogaEquipment() {
    }

    public YogaEquipment(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
