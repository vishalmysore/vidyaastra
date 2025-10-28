package vishal.mysore.fd.model;

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
public class FraudType {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;

    @Relationship(type = "DETECTED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<DetectionMethod> detectionMethods = new HashSet<>();

    @Relationship(type = "HAS_INDICATOR", direction = Relationship.Direction.OUTGOING)
    private Set<FraudIndicator> indicators = new HashSet<>();

    @Relationship(type = "PREVENTED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<PreventionMethod> preventionMethods = new HashSet<>();
}
