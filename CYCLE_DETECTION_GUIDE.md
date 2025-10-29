# Cycle Detection for Fraud Detection Module

## Overview

The Cycle Detection feature uses graph-based algorithms to identify circular patterns in transaction networks. This is especially effective for detecting:

- **Anti-Money Laundering (AML)**: Circular money flows where funds are transferred through multiple accounts and eventually return to the originator
- **Credit Card Fraud**: Impossible travel scenarios where a card is used in multiple locations within an impossible timeframe
- **Identity Theft**: Same identity information used across multiple accounts with suspicious relationships
- **Complex Fraud Schemes**: Multi-layered fraud involving multiple accounts, entities, and transaction types

---

## Architecture

### Components

#### 1. **CycleDetection Model** (`model/CycleDetection.java`)
Entity representing a detected cycle in the transaction network.

**Key Properties:**
- `cycleId`: Unique identifier for the cycle
- `fraudPattern`: Type of fraud pattern (CIRCULAR_MONEY_FLOW, CREDIT_CARD_LOOP, IDENTITY_THEFT_LOOP, GENERIC_CYCLE)
- `cycleLength`: Number of nodes/hops in the cycle
- `totalAmount`: Total transaction amount involved
- `severity`: Risk level (HIGH, MEDIUM, LOW)
- `riskScore`: Calculated risk score (0.0 to 1.0)
- `status`: Workflow status (DETECTED, INVESTIGATING, CONFIRMED, RESOLVED)
- `affectedAccounts`: Comma-separated list of involved accounts
- **Relationships:**
  - `INDICATES` → FraudType
  - `DETECTED_VIA` → DetectionMethod
  - `LINKED_TO` → FraudIndicator

#### 2. **CycleDetectionRepository** (`repository/CycleDetectionRepository.java`)
Data access layer with specialized Neo4j queries for cycle analysis.

**Key Query Methods:**
- `findByFraudPattern(fraudPattern)` - Find cycles by type
- `findHighRiskCycles()` - Get high-risk cycles (riskScore >= 0.8)
- `findMediumRiskCycles()` - Get medium-risk cycles
- `findByStatus(status)` - Filter by investigation status
- `findByCycleLength(length)` - Find cycles with specific depth
- `findConfirmedCycles()` - Get confirmed fraud cases
- `findByTotalAmountGreaterThan(threshold)` - High-value fraud detection
- `findRecentCycles(hours)` - Get recently detected cycles
- `findByLinkedFraudType(fraudTypeName)` - Cross-reference with fraud types

#### 3. **CycleDetectionService** (`service/CycleDetectionService.java`)
Business logic for cycle detection and analysis.

**Core Methods:**

##### Cycle Detection Methods
```java
// Detect circular money flows
List<CycleDetection> detectCircularMoneyFlows()

// Detect credit card fraud loops
List<CycleDetection> detectCreditCardFraudLoops()

// Detect identity theft loops
List<CycleDetection> detectIdentityTheftLoops()

// Generic cycle detection
List<CycleDetection> detectAllCycles(String relationshipType)
```

##### Analysis Methods
```java
// Analyze specific cycle
CycleDetection analyzeCycle(Long cycleId)

// Update cycle status
CycleDetection updateCycleStatus(Long cycleId, String status)

// Get cycles by pattern
List<CycleDetection> getCyclesByPattern(String fraudPattern)

// Get high-risk cycles
List<CycleDetection> getHighRiskCycles()

// Generate fraud report
Map<String, Object> generateFraudDetectionReport()
```

#### 4. **CycleDetectionAlgorithm** (`util/CycleDetectionAlgorithm.java`)
Utility class implementing multiple cycle detection algorithms.

**Algorithms Implemented:**

##### DFS (Depth-First Search) Cycle Detector
- **Complexity**: O(V + E)
- **Use Case**: General-purpose cycle detection
- **Best For**: Medium-sized graphs

```java
DFSCycleDetector dfs = new DFSCycleDetector(graph);
List<List<String>> cycles = dfs.detectAllCycles();
boolean hasCycle = dfs.hasCycle();
```

##### Floyd-Warshall Algorithm
- **Complexity**: O(V³)
- **Use Case**: All-pairs shortest paths with cycle detection
- **Best For**: Finding shortest cycles, detecting negative cycles

```java
FloydWarshallCycleDetector fw = new FloydWarshallCycleDetector(nodes, edges);
fw.computeShortestPaths();
boolean hasNegativeCycle = fw.hasNegativeCycle();
```

##### Tarjan's Algorithm (SCC Detection)
- **Complexity**: O(V + E)
- **Use Case**: Finding Strongly Connected Components
- **Best For**: Identifying groups of mutually reachable accounts

```java
TarjanSCCDetector tarjan = new TarjanSCCDetector(graph);
List<List<String>> sccs = tarjan.findSCCs();
List<List<String>> cycles = tarjan.getCycles();
```

##### Bellman-Ford Algorithm
- **Complexity**: O(V × E)
- **Use Case**: Detecting negative cycles in weighted graphs
- **Best For**: Weighted transaction networks, arbitrage detection

```java
BellmanFordCycleDetector bf = new BellmanFordCycleDetector(nodes, edges);
boolean hasNegativeCycle = bf.hasNegativeCycle(source);
List<String> affectedNodes = bf.getNegativeCycleNodes(source);
```

##### Cycle Analyzer
Utility methods for analyzing detected cycles:
- `getAverageCycleLength()` - Statistical analysis
- `getLongestCycle()` - Identify most complex fraud
- `getShortestCycle()` - Simplest fraud patterns
- `getNodeFrequency()` - How often accounts appear in cycles
- `getMostSuspiciousNodes()` - Identify key actors in fraud

---

## Usage Examples

### 1. Detect Circular Money Flows (Anti-Money Laundering)

```java
@Autowired
private CycleDetectionService cycleDetectionService;

// Detect all circular money flows
List<CycleDetection> cycles = cycleDetectionService.detectCircularMoneyFlows();

// Filter high-risk cases
List<CycleDetection> highRisk = cycles.stream()
    .filter(CycleDetection::isHighRisk)
    .collect(Collectors.toList());

// Analyze each cycle
highRisk.forEach(cycle -> {
    System.out.println("Cycle ID: " + cycle.getCycleId());
    System.out.println("Affected Accounts: " + cycle.getAffectedAccounts());
    System.out.println("Total Amount: $" + cycle.getTotalAmount());
    System.out.println("Risk Score: " + cycle.getRiskScore());
});
```

### 2. Detect Credit Card Fraud

```java
// Detect impossible travel scenarios
List<CycleDetection> fraudLoops = cycleDetectionService.detectCreditCardFraudLoops();

// Get recent detections
List<CycleDetection> recent = cycleDetectionService.getRecentCycles(24); // Last 24 hours

// Get fraud report
Map<String, Object> report = cycleDetectionService.generateFraudDetectionReport();
System.out.println("Total Cycles Detected: " + report.get("totalCyclesDetected"));
System.out.println("High Risk Cycles: " + report.get("highRiskCycles"));
```

### 3. Detect Identity Theft Patterns

```java
// Find identity theft loops
List<CycleDetection> identityTheftCycles = cycleDetectionService.detectIdentityTheftLoops();

// Analyze patterns
identityTheftCycles.forEach(cycle -> {
    cycleDetectionService.updateCycleStatus(cycle.getId(), "INVESTIGATING");
    CycleDetection analyzed = cycleDetectionService.analyzeCycle(cycle.getId());
    System.out.println("Updated Risk Score: " + analyzed.getRiskScore());
});
```

### 4. DFS Cycle Detection

```java
Map<String, List<String>> transactionGraph = new HashMap<>();
transactionGraph.put("Account1", Arrays.asList("Account2", "Account3"));
transactionGraph.put("Account2", Arrays.asList("Account4"));
transactionGraph.put("Account3", Arrays.asList("Account4"));
transactionGraph.put("Account4", Arrays.asList("Account1")); // Cycle!

CycleDetectionAlgorithm.DFSCycleDetector dfs = 
    new CycleDetectionAlgorithm.DFSCycleDetector(transactionGraph);

List<List<String>> cycles = dfs.detectAllCycles();
cycles.forEach(cycle -> 
    System.out.println("Detected Cycle: " + cycle)
);
```

### 5. Tarjan's SCC Algorithm

```java
Map<String, List<String>> graph = buildTransactionGraph();

CycleDetectionAlgorithm.TarjanSCCDetector tarjan = 
    new CycleDetectionAlgorithm.TarjanSCCDetector(graph);

List<List<String>> sccs = tarjan.findSCCs();
List<List<String>> cycles = tarjan.getCycles();

// Get most suspicious nodes
List<String> suspiciousAccounts = 
    CycleDetectionAlgorithm.CycleAnalyzer.getMostSuspiciousNodes(cycles, 5);
```

### 6. Bellman-Ford for Arbitrage Detection

```java
List<String> accounts = Arrays.asList("Bank1", "Bank2", "Bank3");
List<CycleDetectionAlgorithm.Edge> edges = Arrays.asList(
    new CycleDetectionAlgorithm.Edge("Bank1", "Bank2", -0.05), // Negative weight = profit
    new CycleDetectionAlgorithm.Edge("Bank2", "Bank3", -0.03),
    new CycleDetectionAlgorithm.Edge("Bank3", "Bank1", -0.02)
);

CycleDetectionAlgorithm.BellmanFordCycleDetector bf = 
    new CycleDetectionAlgorithm.BellmanFordCycleDetector(accounts, edges);

if (bf.hasNegativeCycle("Bank1")) {
    System.out.println("Arbitrage opportunity detected (potential fraud)");
    List<String> affected = bf.getNegativeCycleNodes("Bank1");
    System.out.println("Affected accounts: " + affected);
}
```

---

## Risk Scoring System

### Risk Score Calculation

```
Risk Score = (Cycle Length Score × 0.4) + (Amount Score × 0.6)

Where:
- Cycle Length Score = min(cycleLength / 10.0, 1.0)
- Amount Score = min(totalAmount / 500000.0, 1.0)
```

### Severity Classification

| Risk Score | Severity | Action |
|-----------|----------|--------|
| 0.80 - 1.0 | HIGH | Immediate investigation required |
| 0.50 - 0.79 | MEDIUM | Schedule for review |
| < 0.50 | LOW | Monitor and log |

---

## Fraud Patterns Detected

### 1. Circular Money Flow (AML)
- Money transferred through multiple accounts
- Eventually returns to originator
- Typical indicators: Long cycle length (5+), high total amount

### 2. Credit Card Fraud Loop
- Card used in multiple locations within impossible timeframe
- Typical indicators: Multiple locations, short time intervals

### 3. Identity Theft Loop
- Same identity information across multiple accounts
- Suspicious account relationships
- Typical indicators: Multiple accounts, similar patterns

### 4. Generic Cycle
- Any other circular pattern in the transaction network
- Can be customized based on relationship types

---

## Database Schema

### Cypher Queries for Setup

```cypher
// Create CycleDetection node
CREATE (c:CycleDetection {
    cycleId: "CYCLE_1698624000000_5234",
    fraudPattern: "CIRCULAR_MONEY_FLOW",
    cycleLength: 4,
    totalAmount: 75000.0,
    severity: "MEDIUM",
    riskScore: 0.65,
    status: "DETECTED",
    detectedAt: datetime()
})

// Link to fraud type
MATCH (c:CycleDetection {cycleId: "CYCLE_1698624000000_5234"}),
      (f:FraudType {name: "Money Laundering"})
CREATE (c)-[:INDICATES]->(f)

// Link to detection method
MATCH (c:CycleDetection {cycleId: "CYCLE_1698624000000_5234"}),
      (d:DetectionMethod {name: "Cycle Detection Algorithm"})
CREATE (c)-[:DETECTED_VIA]->(d)
```

---

## Testing

Run the comprehensive test suite:

```bash
# Run all cycle detection tests
mvn test -Dtest=CycleDetectionServiceTest

# Run with coverage
mvn clean test jacoco:report
```

**Test Coverage Includes:**
- ✅ High-risk cycle detection
- ✅ Medium-risk cycle detection
- ✅ Low-risk cycle detection
- ✅ Status updates
- ✅ Risk score calculations
- ✅ Fraud pattern filtering
- ✅ Recent cycle retrieval
- ✅ Fraud detection report generation
- ✅ Cycle analysis
- ✅ Attribute validation

---

## Performance Considerations

### Algorithm Complexity

| Algorithm | Time Complexity | Space Complexity | Best Use Case |
|-----------|------------------|------------------|---------------|
| DFS | O(V + E) | O(V) | Small to medium graphs |
| Floyd-Warshall | O(V³) | O(V²) | All-pairs shortest paths |
| Tarjan's SCC | O(V + E) | O(V) | Finding components |
| Bellman-Ford | O(V × E) | O(V) | Negative cycles |

### Optimization Tips

1. **For Large Graphs**: Use DFS or Tarjan's algorithm
2. **For Real-time Detection**: Implement incremental cycle detection
3. **For Weighted Graphs**: Use Bellman-Ford with early termination
4. **For Quick Checks**: Use Floyd-Warshall for small, dense graphs

---

## Integration with Other Modules

### Link with FraudType
```java
CycleDetection cycle = new CycleDetection(...);
FraudType moneyLaundering = fraudTypeService.getFraudTypeByName("Money Laundering");
cycle.getFraudTypes().add(moneyLaundering);
```

### Link with DetectionMethod
```java
CycleDetection cycle = ...;
DetectionMethod method = detectionMethodService.getDetectionMethodByName("Cycle Detection");
cycle.getDetectionMethods().add(method);
```

### Link with FraudIndicator
```java
CycleDetection cycle = ...;
FraudIndicator indicator = fraudIndicatorService.getIndicatorByName("Circular Flow");
cycle.getIndicators().add(indicator);
```

---

## Workflow Status

1. **DETECTED** → Cycle identified by algorithm
2. **INVESTIGATING** → Analyst reviewing the cycle
3. **CONFIRMED** → Verified as actual fraud
4. **RESOLVED** → Case closed or fraudulent activity stopped

---

## Future Enhancements

- [ ] Real-time cycle detection with streaming data
- [ ] Machine learning for false positive reduction
- [ ] Temporal analysis of cycle patterns
- [ ] Multi-dimensional cycle detection (amount, time, location)
- [ ] Automated response triggers for high-risk cycles
- [ ] Integration with external AML systems
- [ ] Visual graph representation of cycles

