package vishal.mysore.yoga.util;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;
import vishal.mysore.yoga.model.*;
import vishal.mysore.yoga.service.*;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class YogaDataLoader implements CommandLineRunner {

    private final Neo4jClient neo4jClient;
    private final YogaTypeService yogaTypeService;
    private final YogaPracticeService yogaPracticeService;
    private final YogaBenefitService yogaBenefitService;
    private final YogaEquipmentService yogaEquipmentService;
    private final YogaTechniqueService yogaTechniqueService;

    // Clear all nodes in the database
    private void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        System.out.println("Database cleared successfully");
    }

    // Print counts and node types for debugging
    private void verifyDatabaseCount(String stage) {
        long nodeCount = neo4jClient.query("MATCH (n) RETURN count(n) AS count")
                .fetchAs(Long.class)
                .mappedBy((ts, r) -> r.get("count").asLong())
                .one()
                .orElse(0L);
        System.out.println(stage + " - Total nodes in database: " + nodeCount);

        neo4jClient.query("CALL db.labels() YIELD label RETURN collect(label) as labels")
                .fetchAs(String.class)
                .mappedBy((ts, r) -> r.get("labels").toString())
                .one()
                .ifPresent(labels -> System.out.println("Node types: " + labels));
    }

    @Override
    public void run(String... args) {
        try {
            // Step 1: Clear database
            clearDatabase();
            verifyDatabaseCount("After clearing");

            // Step 2: Create YogaTypes (Core Types)
            YogaType hatha = createYogaType("Hatha Yoga", "Physical practice focusing on postures and breath control", "Core Yoga Type");
            YogaType raja = createYogaType("Raja Yoga", "Meditation and spiritual practice focusing on mind control", "Core Yoga Type");
            YogaType karma = createYogaType("Karma Yoga", "Path of selfless service and action", "Core Yoga Type");
            YogaType bhakti = createYogaType("Bhakti Yoga", "Path of devotion and love", "Core Yoga Type");
            YogaType jnana = createYogaType("Jnana Yoga", "Path of knowledge and wisdom", "Core Yoga Type");
            YogaType kundalini = createYogaType("Kundalini Yoga", "Practice focusing on awakening spiritual energy", "Core Yoga Type");

            // Step 3: Create Modern Yoga Styles
            YogaType vinyasa = createYogaType("Vinyasa", "Flow-based practice linking breath with movement", "Modern Style");
            YogaType ashtanga = createYogaType("Ashtanga", "Dynamic sequence-based practice", "Modern Style");
            YogaType yin = createYogaType("Yin Yoga", "Slow-paced practice holding poses for longer periods", "Modern Style");
            YogaType restorative = createYogaType("Restorative", "Gentle practice using props for complete relaxation", "Modern Style");
            YogaType hotYoga = createYogaType("Hot Yoga", "Practice in heated room (95-105°F)", "Modern Style");

            // Link modern styles to traditional yoga
            hatha.getModernStyles().add(vinyasa);
            hatha.getModernStyles().add(ashtanga);
            hatha.getModernStyles().add(yin);
            hatha.getModernStyles().add(restorative);
            hatha.getModernStyles().add(hotYoga);

            // Set up bidirectional relationships
            vinyasa.getParentStyles().add(hatha);
            ashtanga.getParentStyles().add(hatha);
            yin.getParentStyles().add(hatha);
            restorative.getParentStyles().add(hatha);
            hotYoga.getParentStyles().add(hatha);

            // Update all related entities
            yogaTypeService.updateYogaType(hatha);
            yogaTypeService.updateYogaType(vinyasa);
            yogaTypeService.updateYogaType(ashtanga);
            yogaTypeService.updateYogaType(yin);
            yogaTypeService.updateYogaType(restorative);
            yogaTypeService.updateYogaType(hotYoga);

            // Step 4: Create Core Practices
            YogaPractice asanas = createYogaPractice("Asanas", "Physical postures in yoga", "Core Practice");
            YogaPractice pranayama = createYogaPractice("Pranayama", "Breathing techniques and exercises", "Core Practice");
            YogaPractice meditation = createYogaPractice("Meditation", "Mental focus and mindfulness practices", "Core Practice");
            YogaPractice mudras = createYogaPractice("Mudras", "Hand gestures used in yoga practice", "Core Practice");
            YogaPractice mantras = createYogaPractice("Mantras", "Sacred sounds or phrases for meditation", "Core Practice");

            // Step 5: Create Benefits
            YogaBenefit flexibility = createYogaBenefit("Flexibility", "Increased range of motion and joint mobility", "Physical");
            YogaBenefit strength = createYogaBenefit("Strength", "Enhanced muscular strength and tone", "Physical");
            YogaBenefit balance = createYogaBenefit("Balance", "Improved physical and mental balance", "Physical");
            YogaBenefit stressRelief = createYogaBenefit("Stress Relief", "Reduced stress and anxiety", "Mental");
            YogaBenefit focus = createYogaBenefit("Focus", "Enhanced concentration and mental clarity", "Mental");
            YogaBenefit sleep = createYogaBenefit("Sleep", "Improved sleep quality", "Physical");

            // Step 6: Create Equipment
            YogaEquipment mat = createYogaEquipment("Yoga Mat", "Non-slip surface for practice", "Essential");
            YogaEquipment block = createYogaEquipment("Yoga Block", "Support for poses and alignment", "Prop");
            YogaEquipment strap = createYogaEquipment("Yoga Strap", "Aid for flexibility and reach", "Prop");
            YogaEquipment blanket = createYogaEquipment("Yoga Blanket", "Support and warmth during practice", "Prop");
            YogaEquipment bolster = createYogaEquipment("Bolster", "Support for restorative poses", "Prop");

            // Step 7: Create Techniques
            YogaTechnique ujjayi = createYogaTechnique("Ujjayi", "Ocean breath technique", "Breathing");
            YogaTechnique kapalabhati = createYogaTechnique("Kapalabhati", "Skull-shining breath", "Breathing");
            YogaTechnique nadiShodhana = createYogaTechnique("Nadi Shodhana", "Alternate nostril breathing", "Breathing");

            // Step 8: Link everything together
            // Link practices to yoga types
            hatha.getPractices().addAll(Set.of(asanas, pranayama));
            raja.getPractices().addAll(Set.of(meditation, pranayama));
            kundalini.getPractices().addAll(Set.of(asanas, pranayama, meditation, mudras, mantras));

            // Link benefits to practices
            asanas.getBenefits().addAll(Set.of(flexibility, strength, balance));
            pranayama.getBenefits().addAll(Set.of(stressRelief, focus));
            meditation.getBenefits().addAll(Set.of(stressRelief, focus, sleep));

            // Create restorative practice
            YogaPractice restorativePractice = createYogaPractice("Restorative Practice", "Gentle restorative postures", "Practice");

            // Link equipment to practices
            asanas.getEquipment().addAll(Set.of(mat, block, strap));
            restorativePractice.getEquipment().addAll(Set.of(mat, blanket, bolster));

            // Link practice to type
            restorative.getPractices().add(restorativePractice);

            // Link techniques to practices
            pranayama.getTechniques().addAll(Set.of(ujjayi, kapalabhati, nadiShodhana));

            // Save all updated entities
            yogaTypeService.updateYogaType(hatha);
            yogaTypeService.updateYogaType(raja);
            yogaTypeService.updateYogaType(kundalini);

            yogaPracticeService.updateYogaPractice(asanas);
            yogaPracticeService.updateYogaPractice(pranayama);
            yogaPracticeService.updateYogaPractice(meditation);

            verifyDatabaseCount("Final count");

            // Print summary
            printYogaKnowledgeGraph();

            System.out.println("\nYoga knowledge graph data loaded successfully!");
            System.exit(0);

        } catch (Exception e) {
            System.err.println("Failed to load yoga data: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load yoga data", e);
        }
    }

    private YogaType createYogaType(String name, String description, String category) {
        YogaType type = new YogaType(name, description, category);
        return yogaTypeService.createYogaType(type);
    }

    private YogaPractice createYogaPractice(String name, String description, String category) {
        YogaPractice practice = new YogaPractice(name, description, category);
        return yogaPracticeService.createYogaPractice(practice);
    }

    private YogaBenefit createYogaBenefit(String name, String description, String category) {
        YogaBenefit benefit = new YogaBenefit(name, description, category);
        return yogaBenefitService.createYogaBenefit(benefit);
    }

    private YogaEquipment createYogaEquipment(String name, String description, String category) {
        YogaEquipment equipment = new YogaEquipment(name, description, category);
        return yogaEquipmentService.createYogaEquipment(equipment);
    }

    private YogaTechnique createYogaTechnique(String name, String description, String category) {
        YogaTechnique technique = new YogaTechnique(name, description, category);
        return yogaTechniqueService.createYogaTechnique(technique);
    }

    private void printYogaKnowledgeGraph() {
        System.out.println("\n=== Yoga Types ===");
        yogaTypeService.getAllYogaTypes().forEach(type -> {
            System.out.println("\nType: " + type.getName() + " (" + type.getCategory() + ")");
            System.out.println("Description: " + type.getDescription());

            if (!type.getPractices().isEmpty()) {
                System.out.println("Practices:");
                type.getPractices().forEach(practice ->
                    System.out.println(" - " + practice.getName()));
            }

            if (!type.getModernStyles().isEmpty()) {
                System.out.println("Modern Styles:");
                type.getModernStyles().forEach(style ->
                    System.out.println(" - " + style.getName()));
            }

            if (!type.getParentStyles().isEmpty()) {
                System.out.println("Parent Styles:");
                type.getParentStyles().forEach(style ->
                    System.out.println(" - " + style.getName()));
            }
        });

        System.out.println("\n=== Practices ===");
        yogaPracticeService.getAllYogaPractices().forEach(practice -> {
            System.out.println("\nPractice: " + practice.getName());

            if (!practice.getBenefits().isEmpty()) {
                System.out.println("Benefits:");
                practice.getBenefits().forEach(benefit ->
                    System.out.println(" - " + benefit.getName()));
            }

            if (!practice.getEquipment().isEmpty()) {
                System.out.println("Equipment:");
                practice.getEquipment().forEach(equipment ->
                    System.out.println(" - " + equipment.getName()));
            }

            if (!practice.getTechniques().isEmpty()) {
                System.out.println("Techniques:");
                practice.getTechniques().forEach(technique ->
                    System.out.println(" - " + technique.getName()));
            }
        });
    }
}
