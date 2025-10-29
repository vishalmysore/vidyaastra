package vishal.mysore.fd.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a detected cycle/loop in transaction flow.
 * Used for identifying circular money flows in anti-money laundering applications
 * and detecting fraud patterns like credit card fraud or identity theft.
 */
@Node("CycleDetection")
@Getter
@Setter
public class CycleDetection {
    @Id
    @GeneratedValue
    private Long id;

    private String cycleId;
    private String fraudPattern; // CIRCULAR_MONEY_FLOW, CREDIT_CARD_LOOP, IDENTITY_THEFT_LOOP, etc.
    private String description;
    private Integer cycleLength; // Number of nodes in the cycle
    private Double totalAmount; // Total amount involved in circular transaction
    private String severity; // HIGH, MEDIUM, LOW
    private Double riskScore; // 0.0 to 1.0
    private LocalDateTime detectedAt;
    private String status; // DETECTED, INVESTIGATING, CONFIRMED, RESOLVED
    private String affectedAccounts; // Comma-separated list or JSON

    @Relationship(type = "INDICATES", direction = Relationship.Direction.OUTGOING)
    private Set<FraudType> fraudTypes = new HashSet<>();

    @Relationship(type = "DETECTED_VIA", direction = Relationship.Direction.OUTGOING)
    private Set<DetectionMethod> detectionMethods = new HashSet<>();

    @Relationship(type = "LINKED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<FraudIndicator> indicators = new HashSet<>();

    public CycleDetection() {
        this.detectedAt = LocalDateTime.now();
        this.status = "DETECTED";
    }

    public CycleDetection(String fraudPattern, String description, Integer cycleLength,
                         Double totalAmount, String severity, Double riskScore) {
        this();
        this.fraudPattern = fraudPattern;
        this.description = description;
        this.cycleLength = cycleLength;
        this.totalAmount = totalAmount;
        this.severity = severity;
        this.riskScore = riskScore;
        this.cycleId = generateCycleId();
    }

    private String generateCycleId() {
        return "CYCLE_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    public boolean isHighRisk() {
        return riskScore >= 0.8 || "HIGH".equalsIgnoreCase(severity);
    }

    public boolean isMediumRisk() {
        return riskScore >= 0.5 && riskScore < 0.8;
    }

    public boolean isLowRisk() {
        return riskScore < 0.5;
    }
}

