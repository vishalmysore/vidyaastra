package vishal.mysore.hc.model;

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
public class Symptom {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private Set<Procedure> requiredProcedures = new HashSet<>();

    @Relationship(type = "RELIEVED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Medicine> relievingMedicines = new HashSet<>();
}
