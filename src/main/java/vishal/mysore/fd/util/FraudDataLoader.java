package vishal.mysore.fd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;
import vishal.mysore.fd.model.*;
import vishal.mysore.fd.service.*;

import java.util.List;

@Component
public class FraudDataLoader implements CommandLineRunner {
    private final FraudTypeService fraudTypeService;
    private final DetectionMethodService detectionMethodService;
    private final FraudIndicatorService fraudIndicatorService;
    private final PreventionMethodService preventionMethodService;
    private final DetectionToolService detectionToolService;
    private final Neo4jClient neo4jClient;

    @Autowired
    public FraudDataLoader(
            FraudTypeService fraudTypeService,
            DetectionMethodService detectionMethodService,
            FraudIndicatorService fraudIndicatorService,
            PreventionMethodService preventionMethodService,
            DetectionToolService detectionToolService,
            Neo4jClient neo4jClient) {
        this.fraudTypeService = fraudTypeService;
        this.detectionMethodService = detectionMethodService;
        this.fraudIndicatorService = fraudIndicatorService;
        this.preventionMethodService = preventionMethodService;
        this.detectionToolService = detectionToolService;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            clearDatabase();
            loadFraudKnowledgeGraph();
            printFraudKnowledgeGraph();
            System.out.println("\nData loading and printing completed successfully.");
            System.out.flush();

            // Force exit after completion
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Failed to load fraud data: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        System.out.println("Database cleared successfully");
    }

    private void loadFraudKnowledgeGraph() {
        // Create Fraud Types
        FraudType identityTheft = createFraudType(
            "Identity Theft", 
            "Unauthorized use of personal information for financial gain",
            "Financial Fraud"
        );
        
        FraudType creditCardFraud = createFraudType(
            "Credit Card Fraud",
            "Unauthorized use of credit cards for fraudulent transactions",
            "Payment Fraud"
        );

        FraudType accountTakeover = createFraudType(
            "Account Takeover",
            "Gaining unauthorized access to user accounts",
            "Account Fraud"
        );

        // Create Detection Methods
        DetectionMethod aiDetection = createDetectionMethod(
            "AI-based Detection",
            "Using machine learning algorithms for fraud detection",
            "Machine Learning",
            0.95
        );

        DetectionMethod behavioralAnalysis = createDetectionMethod(
            "Behavioral Analysis",
            "Analyzing user behavior patterns to detect anomalies",
            "Pattern Analysis",
            0.88
        );

        // Create Detection Tools
        DetectionTool fraudAnalyzer = createDetectionTool(
            "Fraud Analyzer Pro",
            "Advanced fraud detection and analysis tool",
            "Securetech",
            "2.1",
            "AI/ML"
        );

        DetectionTool patternDetector = createDetectionTool(
            "Pattern Detective",
            "Pattern-based fraud detection system",
            "CyberGuard",
            "3.0",
            "Big Data Analytics"
        );

        // Create Fraud Indicators
        FraudIndicator multipleFailedLogins = createFraudIndicator(
            "Multiple Failed Logins",
            "Unusually high number of failed login attempts",
            "High",
            0.85
        );

        FraudIndicator unusualLocation = createFraudIndicator(
            "Unusual Location",
            "Transaction attempts from unexpected locations",
            "Medium",
            0.75
        );

        // Create Prevention Methods
        PreventionMethod twoFactorAuth = createPreventionMethod(
            "Two-Factor Authentication",
            "Additional layer of security using multiple verification methods",
            "Authentication",
            "High"
        );

        PreventionMethod encryptedStorage = createPreventionMethod(
            "Encrypted Storage",
            "Secure storage of sensitive data using encryption",
            "Data Security",
            "High"
        );

        // Establish relationships
        aiDetection.getTools().add(fraudAnalyzer);
        behavioralAnalysis.getTools().add(patternDetector);
        detectionMethodService.updateDetectionMethod(aiDetection);
        detectionMethodService.updateDetectionMethod(behavioralAnalysis);

        identityTheft.getDetectionMethods().add(aiDetection);
        identityTheft.getIndicators().add(multipleFailedLogins);
        identityTheft.getPreventionMethods().add(twoFactorAuth);
        fraudTypeService.updateFraudType(identityTheft);

        creditCardFraud.getDetectionMethods().add(behavioralAnalysis);
        creditCardFraud.getIndicators().add(unusualLocation);
        creditCardFraud.getPreventionMethods().add(encryptedStorage);
        fraudTypeService.updateFraudType(creditCardFraud);

        // Add relationships for Account Takeover
        accountTakeover.getDetectionMethods().add(aiDetection);
        accountTakeover.getDetectionMethods().add(behavioralAnalysis);
        accountTakeover.getIndicators().add(multipleFailedLogins);
        accountTakeover.getIndicators().add(unusualLocation);
        accountTakeover.getPreventionMethods().add(twoFactorAuth);
        fraudTypeService.updateFraudType(accountTakeover);
    }

    private void printFraudKnowledgeGraph() {
        System.out.println("\nFraud Detection Knowledge Graph:");
        System.out.println("================================");
        System.out.flush();

        List<FraudType> types = fraudTypeService.getAllFraudTypes();
        for (FraudType type : types) {
            System.out.println("\nFraud Type: " + type.getName());
            System.out.println("Description: " + type.getDescription());
            System.out.println("Category: " + type.getCategory());
            System.out.flush();

            if (!type.getDetectionMethods().isEmpty()) {
                System.out.println("\nDetection Methods:");
                for (DetectionMethod method : type.getDetectionMethods()) {
                    System.out.println(" - " + method.getName() + " (Accuracy: " + method.getAccuracy() + ")");
                    for (DetectionTool tool : method.getTools()) {
                        System.out.println("   * Tool: " + tool.getName() + " by " + tool.getVendor());
                    }
                    System.out.flush();
                }
            }

            if (!type.getIndicators().isEmpty()) {
                System.out.println("\nFraud Indicators:");
                for (FraudIndicator indicator : type.getIndicators()) {
                    System.out.println(" - " + indicator.getName() + " (Confidence: " + indicator.getConfidenceScore() + ")");
                    System.out.flush();
                }
            }

            if (!type.getPreventionMethods().isEmpty()) {
                System.out.println("\nPrevention Methods:");
                for (PreventionMethod method : type.getPreventionMethods()) {
                    System.out.println(" - " + method.getName() + " (Effectiveness: " + method.getEffectiveness() + ")");
                    System.out.flush();
                }
            }

            System.out.println("--------------------------------");
            System.out.flush();
        }
        System.out.println("\nKnowledge Graph printing completed.");
        System.out.flush();
    }

    private FraudType createFraudType(String name, String description, String category) {
        FraudType fraudType = new FraudType();
        fraudType.setName(name);
        fraudType.setDescription(description);
        fraudType.setCategory(category);
        return fraudTypeService.createFraudType(fraudType);
    }

    private DetectionMethod createDetectionMethod(String name, String description, String technique, double accuracy) {
        DetectionMethod method = new DetectionMethod();
        method.setName(name);
        method.setDescription(description);
        method.setTechnique(technique);
        method.setAccuracy(accuracy);
        return detectionMethodService.createDetectionMethod(method);
    }

    private DetectionTool createDetectionTool(String name, String description, String vendor, String version, String technology) {
        DetectionTool tool = new DetectionTool();
        tool.setName(name);
        tool.setDescription(description);
        tool.setVendor(vendor);
        tool.setVersion(version);
        tool.setTechnology(technology);
        return detectionToolService.createDetectionTool(tool);
    }

    private FraudIndicator createFraudIndicator(String name, String description, String severity, double confidenceScore) {
        FraudIndicator indicator = new FraudIndicator();
        indicator.setName(name);
        indicator.setDescription(description);
        indicator.setSeverity(severity);
        indicator.setConfidenceScore(confidenceScore);
        return fraudIndicatorService.createFraudIndicator(indicator);
    }

    private PreventionMethod createPreventionMethod(String name, String description, String strategy, String effectiveness) {
        PreventionMethod method = new PreventionMethod();
        method.setName(name);
        method.setDescription(description);
        method.setStrategy(strategy);
        method.setEffectiveness(effectiveness);
        return preventionMethodService.createPreventionMethod(method);
    }
}
