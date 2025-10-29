package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vishal.mysore.fd.model.CycleDetection;
import vishal.mysore.fd.model.FraudType;
import vishal.mysore.fd.repository.CycleDetectionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for detecting cycles in transaction networks.
 * Implements graph-based cycle detection algorithms for identifying:
 * - Circular money flows (anti-money laundering)
 * - Credit card fraud loops
 * - Identity theft patterns
 * - Complex fraud schemes involving multiple accounts/entities
 */
@Service
public class CycleDetectionService {

    private final CycleDetectionRepository cycleDetectionRepository;
    private final Neo4jClient neo4jClient;
    private final FraudTypeService fraudTypeService;

    @Autowired
    public CycleDetectionService(CycleDetectionRepository cycleDetectionRepository,
                                Neo4jClient neo4jClient,
                                FraudTypeService fraudTypeService) {
        this.cycleDetectionRepository = cycleDetectionRepository;
        this.neo4jClient = neo4jClient;
        this.fraudTypeService = fraudTypeService;
    }

    /**
     * Detect circular money flows in transaction network using DFS algorithm
     * Finds cycles where money flows from one account back to itself through multiple intermediaries
     */
    @Transactional
    public List<CycleDetection> detectCircularMoneyFlows() {
        List<CycleDetection> detectedCycles = new ArrayList<>();

        // Query to find all transactions/transfers relationships in the graph
        String query = "MATCH (a1)-[t:TRANSFER|TRANSACTION*2..10]->(a1) " +
                      "RETURN a1.id as accountId, length(t) as cycleLength, " +
                      "reduce(sum=0, rel in t | sum + rel.amount) as totalAmount";

        neo4jClient.query(query)
                .fetch()
                .all()
                .forEach(record -> {
                    String accountId = (String) record.get("accountId");
                    Integer cycleLength = ((Number) record.get("cycleLength")).intValue();
                    Double totalAmount = ((Number) record.get("totalAmount")).doubleValue();

                    CycleDetection cycle = new CycleDetection(
                            "CIRCULAR_MONEY_FLOW",
                            "Circular money flow detected: Money transferred from account and eventually returned",
                            cycleLength,
                            totalAmount,
                            calculateSeverity(cycleLength, totalAmount),
                            calculateRiskScore(cycleLength, totalAmount)
                    );

                    cycle.setAffectedAccounts(accountId);
                    cycle = cycleDetectionRepository.save(cycle);
                    detectedCycles.add(cycle);
                });

        return detectedCycles;
    }

    /**
     * Detect credit card fraud loops where a card is used in multiple locations
     * in an impossible timeframe, creating a fraud pattern
     */
    @Transactional
    public List<CycleDetection> detectCreditCardFraudLoops() {
        List<CycleDetection> detectedLoops = new ArrayList<>();

        // Query to find credit card transactions in rapid succession across locations
        String query = "MATCH (cc:CreditCard)-[t1:USED_IN]->(l1:Location)," +
                      "(cc)-[t2:USED_IN]->(l2:Location) " +
                      "WHERE t1.timestamp < t2.timestamp AND " +
                      "duration.between(t1.timestamp, t2.timestamp) < duration('PT1H') AND " +
                      "l1 <> l2 " +
                      "RETURN cc.id as cardId, count(distinct l1) as locationCount, " +
                      "sum(t1.amount + t2.amount) as totalAmount";

        neo4jClient.query(query)
                .fetch()
                .all()
                .forEach(record -> {
                    String cardId = (String) record.get("cardId");
                    Integer locationCount = ((Number) record.get("locationCount")).intValue();
                    Double totalAmount = ((Number) record.get("totalAmount")).doubleValue();

                    CycleDetection loop = new CycleDetection(
                            "CREDIT_CARD_LOOP",
                            "Credit card fraud pattern: Impossible travel scenario - card used in multiple locations within impossible timeframe",
                            locationCount,
                            totalAmount,
                            "HIGH",
                            0.9
                    );

                    loop.setAffectedAccounts(cardId);
                    loop = cycleDetectionRepository.save(loop);
                    detectedLoops.add(loop);
                });

        return detectedLoops;
    }

    /**
     * Detect identity theft loops where stolen identity information is used
     * to create fraudulent accounts or transactions in a circular pattern
     */
    @Transactional
    public List<CycleDetection> detectIdentityTheftLoops() {
        List<CycleDetection> detectedLoops = new ArrayList<>();

        // Query to find same identity information used across multiple accounts
        String query = "MATCH (id:IdentityInfo)-[:USED_IN]->(a1:Account)," +
                      "(id)-[:USED_IN]->(a2:Account)," +
                      "(a1)-[rel:HAS_RELATIONSHIP*1..5]->(a2) " +
                      "WHERE a1 <> a2 " +
                      "RETURN id.identifier as identityId, count(distinct a1) as accountCount, " +
                      "collect(distinct a1.id) as accountIds";

        neo4jClient.query(query)
                .fetch()
                .all()
                .forEach(record -> {
                    String identityId = (String) record.get("identityId");
                    Integer accountCount = ((Number) record.get("accountCount")).intValue();
                    Object accountIdsObj = record.get("accountIds");
                    String accountIds = accountIdsObj != null ? accountIdsObj.toString() : "";

                    CycleDetection loop = new CycleDetection(
                            "IDENTITY_THEFT_LOOP",
                            "Identity theft pattern detected: Same identity information used across multiple accounts with suspicious relationships",
                            accountCount,
                            0.0,
                            "HIGH",
                            0.85
                    );

                    loop.setAffectedAccounts(accountIds);
                    loop = cycleDetectionRepository.save(loop);
                    detectedLoops.add(loop);
                });

        return detectedLoops;
    }

    /**
     * Generic cycle detection using depth-first search algorithm
     * Can detect any type of cycle in the transaction graph
     */
    @Transactional
    public List<CycleDetection> detectAllCycles(String relationshipType) {
        List<CycleDetection> allCycles = new ArrayList<>();

        String query = String.format(
                "MATCH p=()-[r:%s*2..10]->() " +
                "WHERE nodes(p)[0] = nodes(p)[last(nodes(p))] " +
                "RETURN nodes(p) as nodes, relationships(p) as relationships, length(p) as pathLength",
                relationshipType
        );

        neo4jClient.query(query)
                .fetch()
                .all()
                .forEach(record -> {
                    Integer pathLength = ((Number) record.get("pathLength")).intValue();

                    CycleDetection cycle = new CycleDetection(
                            "GENERIC_CYCLE",
                            "Generic cycle detected in " + relationshipType + " relationships",
                            pathLength,
                            0.0,
                            calculateSeverityByLength(pathLength),
                            calculateRiskScoreByLength(pathLength)
                    );

                    cycle = cycleDetectionRepository.save(cycle);
                    allCycles.add(cycle);
                });

        return allCycles;
    }

    /**
     * Analyze a detected cycle for fraud patterns and risk assessment
     */
    @Transactional
    public CycleDetection analyzeCycle(Long cycleId) {
        return cycleDetectionRepository.findById(cycleId)
                .map(cycle -> {
                    // Update risk score based on current analysis
                    Double updatedRiskScore = calculateRiskScore(cycle.getCycleLength(), cycle.getTotalAmount());
                    cycle.setRiskScore(updatedRiskScore);

                    // Update severity
                    String updatedSeverity = calculateSeverity(cycle.getCycleLength(), cycle.getTotalAmount());
                    cycle.setSeverity(updatedSeverity);

                    return cycleDetectionRepository.save(cycle);
                })
                .orElse(null);
    }

    /**
     * Update cycle status (for workflow management)
     */
    @Transactional
    public CycleDetection updateCycleStatus(Long cycleId, String status) {
        return cycleDetectionRepository.findById(cycleId)
                .map(cycle -> {
                    cycle.setStatus(status);
                    return cycleDetectionRepository.save(cycle);
                })
                .orElse(null);
    }

    /**
     * Get all high-risk cycles requiring immediate attention
     */
    public List<CycleDetection> getHighRiskCycles() {
        return cycleDetectionRepository.findHighRiskCycles();
    }

    /**
     * Get cycles by fraud pattern
     */
    public List<CycleDetection> getCyclesByPattern(String fraudPattern) {
        return cycleDetectionRepository.findByFraudPattern(fraudPattern);
    }

    /**
     * Get recent cycles detected in specified hours
     */
    public List<CycleDetection> getRecentCycles(Integer hours) {
        return cycleDetectionRepository.findRecentCycles(hours);
    }

    /**
     * Get confirmed fraud cycles
     */
    public List<CycleDetection> getConfirmedCycles() {
        return cycleDetectionRepository.findConfirmedCycles();
    }

    /**
     * Get all cycles
     */
    public List<CycleDetection> getAllCycles() {
        return cycleDetectionRepository.findAllCycles();
    }

    /**
     * Generate fraud detection report
     */
    public Map<String, Object> generateFraudDetectionReport() {
        Map<String, Object> report = new LinkedHashMap<>();

        List<CycleDetection> allCycles = getAllCycles();
        report.put("totalCyclesDetected", allCycles.size());
        report.put("detectionTime", LocalDateTime.now());

        List<CycleDetection> highRiskCycles = getHighRiskCycles();
        report.put("highRiskCycles", highRiskCycles.size());

        Map<String, Integer> patternCounts = allCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getFraudPattern,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                List::size
                        )
                ));
        report.put("fraudPatternDistribution", patternCounts);

        Double totalAmountAtRisk = allCycles.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .sum();
        report.put("totalAmountAtRisk", totalAmountAtRisk);

        Map<String, Integer> statusDistribution = allCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getStatus,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                List::size
                        )
                ));
        report.put("statusDistribution", statusDistribution);

        return report;
    }

    /**
     * Calculate severity based on cycle characteristics
     */
    private String calculateSeverity(Integer cycleLength, Double totalAmount) {
        if (cycleLength >= 5 || totalAmount > 100000) {
            return "HIGH";
        } else if (cycleLength >= 3 || totalAmount > 50000) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    /**
     * Calculate risk score (0.0 to 1.0)
     */
    private Double calculateRiskScore(Integer cycleLength, Double totalAmount) {
        Double lengthScore = Math.min(cycleLength / 10.0, 1.0);
        Double amountScore = Math.min(totalAmount / 500000.0, 1.0);
        return (lengthScore * 0.4) + (amountScore * 0.6);
    }

    /**
     * Calculate severity by cycle length only
     */
    private String calculateSeverityByLength(Integer cycleLength) {
        if (cycleLength >= 5) {
            return "HIGH";
        } else if (cycleLength >= 3) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    /**
     * Calculate risk score by cycle length only
     */
    private Double calculateRiskScoreByLength(Integer cycleLength) {
        return Math.min(cycleLength / 10.0, 1.0);
    }
}

