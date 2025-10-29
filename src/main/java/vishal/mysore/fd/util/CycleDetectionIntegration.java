package vishal.mysore.fd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vishal.mysore.fd.model.CycleDetection;
import vishal.mysore.fd.model.FraudType;
import vishal.mysore.fd.service.CycleDetectionService;
import vishal.mysore.fd.service.FraudTypeService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Integration utility for Cycle Detection with other Fraud Detection components.
 * Provides practical examples and helper methods for using cycle detection in real-world scenarios.
 */
@Component
public class CycleDetectionIntegration {

    private final CycleDetectionService cycleDetectionService;
    private final FraudTypeService fraudTypeService;

    @Autowired
    public CycleDetectionIntegration(CycleDetectionService cycleDetectionService,
                                    FraudTypeService fraudTypeService) {
        this.cycleDetectionService = cycleDetectionService;
        this.fraudTypeService = fraudTypeService;
    }

    /**
     * Comprehensive AML (Anti-Money Laundering) fraud detection workflow
     */
    public Map<String, Object> runAMLDetectionWorkflow() {
        Map<String, Object> workflow = new LinkedHashMap<>();

        // Step 1: Detect all circular money flows
        List<CycleDetection> allCycles = cycleDetectionService.detectCircularMoneyFlows();
        workflow.put("step1_total_cycles_detected", allCycles.size());

        // Step 2: Separate by risk level
        List<CycleDetection> highRiskCycles = allCycles.stream()
                .filter(CycleDetection::isHighRisk)
                .collect(Collectors.toList());

        List<CycleDetection> mediumRiskCycles = allCycles.stream()
                .filter(CycleDetection::isMediumRisk)
                .collect(Collectors.toList());

        workflow.put("step2_high_risk_count", highRiskCycles.size());
        workflow.put("step2_medium_risk_count", mediumRiskCycles.size());

        // Step 3: Update status to INVESTIGATING for high-risk cycles
        highRiskCycles.forEach(cycle ->
                cycleDetectionService.updateCycleStatus(cycle.getId(), "INVESTIGATING")
        );
        workflow.put("step3_updated_to_investigating", highRiskCycles.size());

        // Step 4: Link to Money Laundering fraud type
        FraudType moneyLaundering = fraudTypeService.getFraudTypeByName("Money Laundering");
        if (moneyLaundering != null) {
            highRiskCycles.forEach(cycle ->
                    cycle.getFraudTypes().add(moneyLaundering)
            );
            workflow.put("step4_linked_to_fraud_type", true);
        }

        // Step 5: Generate report
        Map<String, Object> report = cycleDetectionService.generateFraudDetectionReport();
        workflow.put("step5_fraud_report", report);

        return workflow;
    }

    /**
     * Credit card fraud detection workflow for impossible travel scenarios
     */
    public Map<String, Object> runCreditCardFraudDetection() {
        Map<String, Object> detection = new LinkedHashMap<>();

        // Detect impossible travel patterns
        List<CycleDetection> cardFraudCycles = cycleDetectionService.detectCreditCardFraudLoops();
        detection.put("credit_card_fraud_cycles", cardFraudCycles.size());

        // All credit card fraud is HIGH risk - no need to filter
        detection.put("all_high_risk", true);

        // Update all to CONFIRMED status (credit card fraud is usually conclusive)
        cardFraudCycles.forEach(cycle -> {
            cycleDetectionService.updateCycleStatus(cycle.getId(), "CONFIRMED");
            cycleDetectionService.analyzeCycle(cycle.getId());
        });

        detection.put("cycles_confirmed", cardFraudCycles.size());

        // Get detailed information
        List<Map<String, Object>> details = cardFraudCycles.stream()
                .map(cycle -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("cycleId", cycle.getCycleId());
                    map.put("pattern", cycle.getFraudPattern());
                    map.put("affectedAccounts", cycle.getAffectedAccounts());
                    map.put("totalAmount", cycle.getTotalAmount());
                    map.put("riskScore", cycle.getRiskScore());
                    return map;
                })
                .collect(Collectors.toList());

        detection.put("detailed_cycles", details);
        return detection;
    }

    /**
     * Identity theft detection workflow
     */
    public Map<String, Object> runIdentityTheftDetection() {
        Map<String, Object> detection = new LinkedHashMap<>();

        // Detect identity theft loops
        List<CycleDetection> identityTheftCycles = cycleDetectionService.detectIdentityTheftLoops();
        detection.put("identity_theft_cycles_detected", identityTheftCycles.size());

        // Link to Identity Theft fraud type
        FraudType identityTheft = fraudTypeService.getFraudTypeByName("Identity Theft");
        if (identityTheft != null) {
            identityTheftCycles.forEach(cycle ->
                    cycle.getFraudTypes().add(identityTheft)
            );
            detection.put("linked_to_identity_theft_type", true);
        }

        // Update status
        identityTheftCycles.forEach(cycle ->
                cycleDetectionService.updateCycleStatus(cycle.getId(), "INVESTIGATING")
        );

        detection.put("status_updated_to_investigating", identityTheftCycles.size());
        return detection;
    }

    /**
     * Monitor recent fraud detections (useful for dashboards and alerts)
     */
    public Map<String, Object> getRecentFraudSummary(Integer hours) {
        Map<String, Object> summary = new LinkedHashMap<>();

        List<CycleDetection> recentCycles = cycleDetectionService.getRecentCycles(hours);
        summary.put("hours_back", hours);
        summary.put("total_cycles", recentCycles.size());

        // Group by fraud pattern
        Map<String, Long> patternDistribution = recentCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getFraudPattern,
                        Collectors.counting()
                ));
        summary.put("pattern_distribution", patternDistribution);

        // Group by severity
        Map<String, Long> severityDistribution = recentCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getSeverity,
                        Collectors.counting()
                ));
        summary.put("severity_distribution", severityDistribution);

        // Calculate total amount at risk
        Double totalAmountAtRisk = recentCycles.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .sum();
        summary.put("total_amount_at_risk", totalAmountAtRisk);

        // Get top suspects (accounts appearing most frequently)
        Map<String, Integer> suspectFrequency = new HashMap<>();
        recentCycles.forEach(cycle -> {
            String[] accounts = cycle.getAffectedAccounts().split(",");
            for (String account : accounts) {
                suspectFrequency.put(account.trim(),
                        suspectFrequency.getOrDefault(account.trim(), 0) + 1);
            }
        });

        List<String> topSuspects = suspectFrequency.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        summary.put("top_suspect_accounts", topSuspects);
        return summary;
    }

    /**
     * Generate compliance report for regulatory requirements
     */
    public Map<String, Object> generateComplianceReport() {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("report_timestamp", System.currentTimeMillis());

        // Get all cycles
        List<CycleDetection> allCycles = cycleDetectionService.getAllCycles();
        report.put("total_cycles_detected", allCycles.size());

        // Breakdown by status
        Map<String, Long> statusBreakdown = allCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getStatus,
                        Collectors.counting()
                ));
        report.put("status_breakdown", statusBreakdown);

        // High-risk summary
        List<CycleDetection> highRisk = cycleDetectionService.getHighRiskCycles();
        report.put("high_risk_cycles", highRisk.size());

        // Total amount at risk
        Double totalAmount = allCycles.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .sum();
        report.put("total_amount_at_risk", totalAmount);

        // Confirmed fraud cases
        List<CycleDetection> confirmed = cycleDetectionService.getConfirmedCycles();
        report.put("confirmed_fraud_cases", confirmed.size());

        Double confirmedAmount = confirmed.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .sum();
        report.put("confirmed_fraud_amount", confirmedAmount);

        // Average risk score
        Double averageRiskScore = allCycles.stream()
                .mapToDouble(CycleDetection::getRiskScore)
                .average()
                .orElse(0.0);
        report.put("average_risk_score", averageRiskScore);

        // Fraud pattern breakdown
        Map<String, Long> patternBreakdown = allCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getFraudPattern,
                        Collectors.counting()
                ));
        report.put("fraud_pattern_breakdown", patternBreakdown);

        return report;
    }

    /**
     * Alert system for high-risk cycles requiring immediate action
     */
    public List<Map<String, Object>> getHighRiskAlerts() {
        List<CycleDetection> highRiskCycles = cycleDetectionService.getHighRiskCycles();

        return highRiskCycles.stream()
                .map(cycle -> {
                    Map<String, Object> alert = new LinkedHashMap<>();
                    alert.put("alert_id", cycle.getCycleId());
                    alert.put("severity", cycle.getSeverity());
                    alert.put("risk_score", cycle.getRiskScore());
                    alert.put("fraud_pattern", cycle.getFraudPattern());
                    alert.put("total_amount", cycle.getTotalAmount());
                    alert.put("cycle_length", cycle.getCycleLength());
                    alert.put("affected_accounts", cycle.getAffectedAccounts());
                    alert.put("detected_at", cycle.getDetectedAt());
                    alert.put("action_required", "IMMEDIATE_INVESTIGATION");
                    return alert;
                })
                .collect(Collectors.toList());
    }

    /**
     * Trend analysis for fraud patterns over time
     */
    public Map<String, Object> analyzeFraudTrends() {
        Map<String, Object> trends = new LinkedHashMap<>();

        // Get all cycles for trend analysis
        List<CycleDetection> allCycles = cycleDetectionService.getAllCycles();

        // Trend 1: Most common fraud pattern
        Map<String, Long> patternFrequency = allCycles.stream()
                .collect(Collectors.groupingBy(
                        CycleDetection::getFraudPattern,
                        Collectors.counting()
                ));
        String mostCommonPattern = patternFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("NONE");
        trends.put("most_common_pattern", mostCommonPattern);

        // Trend 2: Average cycle length
        Double avgCycleLength = allCycles.stream()
                .mapToDouble(CycleDetection::getCycleLength)
                .average()
                .orElse(0.0);
        trends.put("average_cycle_length", avgCycleLength);

        // Trend 3: Average transaction amount
        Double avgAmount = allCycles.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .average()
                .orElse(0.0);
        trends.put("average_transaction_amount", avgAmount);

        // Trend 4: Risk distribution
        long highRiskCount = allCycles.stream().filter(CycleDetection::isHighRisk).count();
        long mediumRiskCount = allCycles.stream().filter(CycleDetection::isMediumRisk).count();
        long lowRiskCount = allCycles.stream().filter(CycleDetection::isLowRisk).count();

        trends.put("risk_distribution", Map.of(
                "HIGH", highRiskCount,
                "MEDIUM", mediumRiskCount,
                "LOW", lowRiskCount
        ));

        // Trend 5: Detection success rate
        long confirmed = cycleDetectionService.getConfirmedCycles().size();
        Double successRate = allCycles.isEmpty() ? 0.0 : (double) confirmed / allCycles.size();
        trends.put("confirmed_fraud_rate", String.format("%.2f%%", successRate * 100));

        return trends;
    }

    /**
     * Batch analysis for processing multiple cycles
     */
    public Map<String, Object> batchAnalyzeAndUpdate(String fraudPattern) {
        Map<String, Object> result = new LinkedHashMap<>();

        // Get all cycles for pattern
        List<CycleDetection> cycles = cycleDetectionService.getCyclesByPattern(fraudPattern);
        result.put("pattern", fraudPattern);
        result.put("total_cycles", cycles.size());

        // Update unanalyzed cycles
        int updated = 0;
        for (CycleDetection cycle : cycles) {
            if ("DETECTED".equals(cycle.getStatus())) {
                CycleDetection analyzed = cycleDetectionService.analyzeCycle(cycle.getId());
                if ("DETECTED".equals(analyzed.getStatus())) {
                    updated++;
                }
            }
        }

        result.put("cycles_analyzed", updated);

        // Summary statistics
        Double totalAmount = cycles.stream()
                .mapToDouble(CycleDetection::getTotalAmount)
                .sum();
        result.put("total_amount_involved", totalAmount);

        Double avgRiskScore = cycles.stream()
                .mapToDouble(CycleDetection::getRiskScore)
                .average()
                .orElse(0.0);
        result.put("average_risk_score", avgRiskScore);

        return result;
    }
}

