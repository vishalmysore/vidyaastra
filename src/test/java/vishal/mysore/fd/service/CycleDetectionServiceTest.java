package vishal.mysore.fd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.fd.model.CycleDetection;
import vishal.mysore.fd.repository.CycleDetectionRepository;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Fraud Detection - Cycle Detection Service Tests")
class CycleDetectionServiceTest {

    @Mock
    private CycleDetectionRepository cycleDetectionRepository;

    @Mock
    private Neo4jClient neo4jClient;

    @Mock
    private FraudTypeService fraudTypeService;

    @InjectMocks
    private CycleDetectionService cycleDetectionService;

    private CycleDetection testCycle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCycle = new CycleDetection();
        testCycle.setId(1L);
        testCycle.setCycleId("CYCLE_1698624000000_5234");
        testCycle.setFraudPattern("CIRCULAR_MONEY_FLOW");
        testCycle.setDescription("Circular money flow detected in transaction network");
        testCycle.setCycleLength(4);
        testCycle.setTotalAmount(75000.0);
        testCycle.setSeverity("MEDIUM");
        testCycle.setRiskScore(0.65);
        testCycle.setStatus("DETECTED");
        testCycle.setAffectedAccounts("ACC001,ACC002,ACC003,ACC004");
    }

    @Test
    @DisplayName("Should create cycle detection successfully")
    void testCreateCycleDetection() {
        when(cycleDetectionRepository.save(any(CycleDetection.class))).thenReturn(testCycle);

        CycleDetection savedCycle = cycleDetectionRepository.save(testCycle);

        assertNotNull(savedCycle);
        assertEquals("CIRCULAR_MONEY_FLOW", savedCycle.getFraudPattern());
        assertEquals(4, savedCycle.getCycleLength());
        verify(cycleDetectionRepository, times(1)).save(testCycle);
    }

    @Test
    @DisplayName("Should detect high risk cycles")
    void testDetectHighRiskCycles() {
        CycleDetection highRiskCycle = new CycleDetection(
                "CREDIT_CARD_LOOP",
                "High-risk credit card fraud detected",
                5,
                150000.0,
                "HIGH",
                0.95
        );

        List<CycleDetection> highRiskCycles = Arrays.asList(highRiskCycle);
        when(cycleDetectionRepository.findHighRiskCycles()).thenReturn(highRiskCycles);

        List<CycleDetection> result = cycleDetectionService.getHighRiskCycles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isHighRisk());
        verify(cycleDetectionRepository, times(1)).findHighRiskCycles();
    }

    @Test
    @DisplayName("Should get cycles by fraud pattern")
    void testGetCyclesByPattern() {
        List<CycleDetection> cycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findByFraudPattern("CIRCULAR_MONEY_FLOW"))
                .thenReturn(cycles);

        List<CycleDetection> result = cycleDetectionService.getCyclesByPattern("CIRCULAR_MONEY_FLOW");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CIRCULAR_MONEY_FLOW", result.get(0).getFraudPattern());
        verify(cycleDetectionRepository, times(1)).findByFraudPattern("CIRCULAR_MONEY_FLOW");
    }

    @Test
    @DisplayName("Should get recent cycles")
    void testGetRecentCycles() {
        List<CycleDetection> recentCycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findRecentCycles(24)).thenReturn(recentCycles);

        List<CycleDetection> result = cycleDetectionService.getRecentCycles(24);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cycleDetectionRepository, times(1)).findRecentCycles(24);
    }

    @Test
    @DisplayName("Should get confirmed cycles")
    void testGetConfirmedCycles() {
        testCycle.setStatus("CONFIRMED");
        List<CycleDetection> confirmedCycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findConfirmedCycles()).thenReturn(confirmedCycles);

        List<CycleDetection> result = cycleDetectionService.getConfirmedCycles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CONFIRMED", result.get(0).getStatus());
        verify(cycleDetectionRepository, times(1)).findConfirmedCycles();
    }

    @Test
    @DisplayName("Should update cycle status")
    void testUpdateCycleStatus() {
        when(cycleDetectionRepository.findById(1L)).thenReturn(Optional.of(testCycle));
        testCycle.setStatus("INVESTIGATING");
        when(cycleDetectionRepository.save(any(CycleDetection.class))).thenReturn(testCycle);

        CycleDetection updatedCycle = cycleDetectionService.updateCycleStatus(1L, "INVESTIGATING");

        assertNotNull(updatedCycle);
        assertEquals("INVESTIGATING", updatedCycle.getStatus());
        verify(cycleDetectionRepository, times(1)).findById(1L);
        verify(cycleDetectionRepository, times(1)).save(any(CycleDetection.class));
    }

    @Test
    @DisplayName("Should analyze cycle and update risk score")
    void testAnalyzeCycle() {
        when(cycleDetectionRepository.findById(1L)).thenReturn(Optional.of(testCycle));
        when(cycleDetectionRepository.save(any(CycleDetection.class))).thenReturn(testCycle);

        CycleDetection analyzedCycle = cycleDetectionService.analyzeCycle(1L);

        assertNotNull(analyzedCycle);
        assertNotNull(analyzedCycle.getRiskScore());
        verify(cycleDetectionRepository, times(1)).findById(1L);
        verify(cycleDetectionRepository, times(1)).save(any(CycleDetection.class));
    }

    @Test
    @DisplayName("Should validate high risk cycle")
    void testValidateHighRiskCycle() {
        CycleDetection highRiskCycle = new CycleDetection(
                "IDENTITY_THEFT_LOOP",
                "Multiple accounts with same identity",
                6,
                200000.0,
                "HIGH",
                0.90
        );

        assertTrue(highRiskCycle.isHighRisk());
        assertEquals("IDENTITY_THEFT_LOOP", highRiskCycle.getFraudPattern());
    }

    @Test
    @DisplayName("Should validate medium risk cycle")
    void testValidateMediumRiskCycle() {
        assertTrue(testCycle.isMediumRisk());
        assertEquals("MEDIUM", testCycle.getSeverity());
    }

    @Test
    @DisplayName("Should validate low risk cycle")
    void testValidateLowRiskCycle() {
        CycleDetection lowRiskCycle = new CycleDetection(
                "GENERIC_CYCLE",
                "Minor cycle detected",
                2,
                10000.0,
                "LOW",
                0.25
        );

        assertTrue(lowRiskCycle.isLowRisk());
        assertEquals("LOW", lowRiskCycle.getSeverity());
    }

    @Test
    @DisplayName("Should get all cycles")
    void testGetAllCycles() {
        List<CycleDetection> allCycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findAllCycles()).thenReturn(allCycles);

        List<CycleDetection> result = cycleDetectionService.getAllCycles();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cycleDetectionRepository, times(1)).findAllCycles();
    }

    @Test
    @DisplayName("Should generate fraud detection report")
    void testGenerateFraudDetectionReport() {
        List<CycleDetection> allCycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findAllCycles()).thenReturn(allCycles);
        when(cycleDetectionRepository.findHighRiskCycles()).thenReturn(Arrays.asList());

        Map<String, Object> report = cycleDetectionService.generateFraudDetectionReport();

        assertNotNull(report);
        assertTrue(report.containsKey("totalCyclesDetected"));
        assertTrue(report.containsKey("highRiskCycles"));
        assertTrue(report.containsKey("fraudPatternDistribution"));
        assertTrue(report.containsKey("totalAmountAtRisk"));
        assertTrue(report.containsKey("statusDistribution"));
        assertEquals(1, report.get("totalCyclesDetected"));
    }

    @Test
    @DisplayName("Should validate cycle detection attributes")
    void testCycleDetectionAttributes() {
        assertEquals(1L, testCycle.getId());
        assertEquals("CIRCULAR_MONEY_FLOW", testCycle.getFraudPattern());
        assertEquals(4, testCycle.getCycleLength());
        assertEquals(75000.0, testCycle.getTotalAmount());
        assertEquals("MEDIUM", testCycle.getSeverity());
        assertEquals(0.65, testCycle.getRiskScore());
        assertEquals("DETECTED", testCycle.getStatus());
        assertNotNull(testCycle.getDetectedAt());
    }

    @Test
    @DisplayName("Should find cycle by cycle ID")
    void testFindByCycleId() {
        when(cycleDetectionRepository.findByCycleId("CYCLE_1698624000000_5234"))
                .thenReturn(testCycle);

        CycleDetection result = cycleDetectionRepository.findByCycleId("CYCLE_1698624000000_5234");

        assertNotNull(result);
        assertEquals("CYCLE_1698624000000_5234", result.getCycleId());
        verify(cycleDetectionRepository, times(1)).findByCycleId("CYCLE_1698624000000_5234");
    }

    @Test
    @DisplayName("Should find cycles by total amount threshold")
    void testFindByTotalAmountGreaterThan() {
        List<CycleDetection> cycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findByTotalAmountGreaterThan(50000.0))
                .thenReturn(cycles);

        List<CycleDetection> result = cycleDetectionRepository.findByTotalAmountGreaterThan(50000.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTotalAmount() > 50000.0);
        verify(cycleDetectionRepository, times(1)).findByTotalAmountGreaterThan(50000.0);
    }

    @Test
    @DisplayName("Should find cycles by cycle length")
    void testFindByCycleLength() {
        List<CycleDetection> cycles = Arrays.asList(testCycle);
        when(cycleDetectionRepository.findByCycleLength(4)).thenReturn(cycles);

        List<CycleDetection> result = cycleDetectionRepository.findByCycleLength(4);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getCycleLength());
        verify(cycleDetectionRepository, times(1)).findByCycleLength(4);
    }

    @Test
    @DisplayName("Should return empty list when no cycles found")
    void testGetAllCyclesEmpty() {
        when(cycleDetectionRepository.findAllCycles()).thenReturn(Arrays.asList());

        List<CycleDetection> cycles = cycleDetectionService.getAllCycles();

        assertNotNull(cycles);
        assertTrue(cycles.isEmpty());
        verify(cycleDetectionRepository, times(1)).findAllCycles();
    }
}

