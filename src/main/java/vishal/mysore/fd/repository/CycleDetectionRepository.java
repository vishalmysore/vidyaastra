package vishal.mysore.fd.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import vishal.mysore.fd.model.CycleDetection;

import java.util.List;

/**
 * Repository for CycleDetection entities.
 * Provides database access methods for cycle detection queries and analysis.
 */
@Repository
public interface CycleDetectionRepository extends Neo4jRepository<CycleDetection, Long> {

    /**
     * Find all cycles by fraud pattern
     */
    @Query("MATCH (c:CycleDetection) WHERE c.fraudPattern = $fraudPattern RETURN c")
    List<CycleDetection> findByFraudPattern(String fraudPattern);

    /**
     * Find all high-risk cycles
     */
    @Query("MATCH (c:CycleDetection) WHERE c.riskScore >= 0.8 OR c.severity = 'HIGH' RETURN c ORDER BY c.riskScore DESC")
    List<CycleDetection> findHighRiskCycles();

    /**
     * Find all medium-risk cycles
     */
    @Query("MATCH (c:CycleDetection) WHERE c.riskScore >= 0.5 AND c.riskScore < 0.8 RETURN c ORDER BY c.riskScore DESC")
    List<CycleDetection> findMediumRiskCycles();

    /**
     * Find cycles by status
     */
    @Query("MATCH (c:CycleDetection) WHERE c.status = $status RETURN c ORDER BY c.detectedAt DESC")
    List<CycleDetection> findByStatus(String status);

    /**
     * Find cycles with specific cycle length (useful for identifying specific pattern types)
     */
    @Query("MATCH (c:CycleDetection) WHERE c.cycleLength = $length RETURN c ORDER BY c.riskScore DESC")
    List<CycleDetection> findByCycleLength(Integer length);

    /**
     * Find all confirmed fraud cycles
     */
    @Query("MATCH (c:CycleDetection) WHERE c.status = 'CONFIRMED' RETURN c ORDER BY c.totalAmount DESC")
    List<CycleDetection> findConfirmedCycles();

    /**
     * Find cycles by cycle ID (unique identifier)
     */
    @Query("MATCH (c:CycleDetection) WHERE c.cycleId = $cycleId RETURN c")
    CycleDetection findByCycleId(String cycleId);

    /**
     * Find cycles with total amount greater than threshold (for high-value fraud detection)
     */
    @Query("MATCH (c:CycleDetection) WHERE c.totalAmount > $threshold RETURN c ORDER BY c.totalAmount DESC")
    List<CycleDetection> findByTotalAmountGreaterThan(Double threshold);

    /**
     * Find cycles linked to specific fraud type
     */
    @Query("MATCH (c:CycleDetection)-[:INDICATES]->(f:FraudType) WHERE f.name = $fraudTypeName RETURN c")
    List<CycleDetection> findByLinkedFraudType(String fraudTypeName);

    /**
     * Find recent cycles detected in last N hours
     */
    @Query("MATCH (c:CycleDetection) WHERE c.detectedAt > datetime() - duration('PT' + $hours + 'H') RETURN c ORDER BY c.detectedAt DESC")
    List<CycleDetection> findRecentCycles(Integer hours);

    /**
     * Count cycles by fraud pattern
     */
    @Query("MATCH (c:CycleDetection) WHERE c.fraudPattern = $fraudPattern RETURN count(c)")
    Integer countByFraudPattern(String fraudPattern);

    /**
     * Find all cycles (for analysis and reporting)
     */
    @Query("MATCH (c:CycleDetection) RETURN c ORDER BY c.detectedAt DESC")
    List<CycleDetection> findAllCycles();
}

