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
public class FraudIndicator {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String severity;
    private double confidenceScore;

    @Relationship(type = "HAS_INDICATOR", direction = Relationship.Direction.INCOMING)
    private Set<FraudType> fraudTypes = new HashSet<>();

    @Relationship(type = "DETECTS_INDICATOR", direction = Relationship.Direction.INCOMING)
    private Set<DetectionMethod> detectionMethods = new HashSet();
}
