package vishal.mysore.hc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;
import vishal.mysore.hc.model.*;
import vishal.mysore.hc.service.*;

@Component
public class HealthcareDataLoader implements CommandLineRunner {
    private final PatientService patientService;
    private final ConditionService conditionService;
    private final MedicineService medicineService;
    private final SymptomService symptomService;
    private final TreatmentService treatmentService;
    private final ReactionService reactionService;
    private final DepartmentService departmentService;
    private final ProcedureService procedureService;
    private final DiagnosisService diagnosisService;
    private final Neo4jClient neo4jClient;

    @Autowired
    public HealthcareDataLoader(
            PatientService patientService,
            ConditionService conditionService,
            MedicineService medicineService,
            SymptomService symptomService,
            TreatmentService treatmentService,
            ReactionService reactionService,
            DepartmentService departmentService,
            ProcedureService procedureService,
            DiagnosisService diagnosisService,
            Neo4jClient neo4jClient) {
        this.patientService = patientService;
        this.conditionService = conditionService;
        this.medicineService = medicineService;
        this.symptomService = symptomService;
        this.treatmentService = treatmentService;
        this.reactionService = reactionService;
        this.departmentService = departmentService;
        this.procedureService = procedureService;
        this.diagnosisService = diagnosisService;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            clearDatabase();
            loadHealthcareKnowledgeGraph();
            printHealthcareKnowledgeGraph();
            System.out.println("\nData loading and printing completed successfully.");
            System.out.flush();
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Failed to load healthcare data: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        System.out.println("Database cleared successfully");
    }

    private void loadHealthcareKnowledgeGraph() {
        // Create Departments
        Department cardiology = createDepartment(
            "Cardiology",
            "Heart and cardiovascular system specialists"
        );

        Department neurology = createDepartment(
            "Neurology",
            "Brain and nervous system specialists"
        );

        Department orthopedics = createDepartment(
            "Orthopedics",
            "Bone and joint specialists"
        );

        // Create Conditions
        Condition hypertension = createCondition(
            "Hypertension",
            "High blood pressure condition",
            "Cardiovascular"
        );

        Condition arthritis = createCondition(
            "Arthritis",
            "Joint inflammation condition",
            "Musculoskeletal"
        );

        Condition migraine = createCondition(
            "Migraine",
            "Severe headache condition",
            "Neurological"
        );

        // Create Symptoms
        Symptom headache = createSymptom(
            "Headache",
            "Pain in the head region"
        );

        Symptom jointPain = createSymptom(
            "Joint Pain",
            "Pain in joints"
        );

        Symptom dizziness = createSymptom(
            "Dizziness",
            "Feeling of unsteadiness"
        );

        // Create Medicines
        Medicine lisinopril = createMedicine(
            "Lisinopril",
            "ACE inhibitor for blood pressure"
        );

        Medicine ibuprofen = createMedicine(
            "Ibuprofen",
            "Anti-inflammatory medication"
        );

        Medicine sumatriptan = createMedicine(
            "Sumatriptan",
            "Migraine relief medication"
        );

        // Create Procedures
        Procedure bloodPressureTest = createProcedure(
            "Blood Pressure Test",
            "Measuring blood pressure",
            "Diagnostic"
        );

        Procedure mri = createProcedure(
            "MRI Scan",
            "Magnetic resonance imaging",
            "Imaging"
        );

        Procedure xray = createProcedure(
            "X-Ray",
            "Radiographic imaging",
            "Imaging"
        );

        // Create Treatments
        Treatment bpManagement = createTreatment(
            "BP Management",
            "Blood pressure management protocol"
        );

        Treatment painManagement = createTreatment(
            "Pain Management",
            "Pain control protocol"
        );

        // Create Reactions
        Reaction cough = createReaction(
            "Dry Cough",
            "Persistent dry cough",
            "Mild"
        );

        Reaction stomachIrritation = createReaction(
            "Stomach Irritation",
            "Gastric discomfort",
            "Moderate"
        );

        // Create Diagnoses
        Diagnosis bpDiagnosis = createDiagnosis(
            "BP Diagnosis",
            "Blood pressure evaluation",
            "Primary"
        );

        Diagnosis arthritisDiagnosis = createDiagnosis(
            "Arthritis Diagnosis",
            "Joint condition evaluation",
            "Chronic"
        );

        // Create Patient
        Patient patient = createPatient(
            "John Doe",
            "Patient with multiple conditions"
        );

        // Establish relationships

        // Department specializations
        cardiology.getSpecializedConditions().add(hypertension);
        neurology.getSpecializedConditions().add(migraine);
        orthopedics.getSpecializedConditions().add(arthritis);

        // Department procedures
        cardiology.getProcedures().add(bloodPressureTest);
        neurology.getProcedures().add(mri);
        orthopedics.getProcedures().add(xray);

        // Condition symptoms
        hypertension.getSymptoms().add(dizziness);
        arthritis.getSymptoms().add(jointPain);
        migraine.getSymptoms().add(headache);
        migraine.getSymptoms().add(dizziness);

        // Medicine treatments
        lisinopril.getTreatedConditions().add(hypertension);
        ibuprofen.getTreatedConditions().add(arthritis);
        sumatriptan.getTreatedConditions().add(migraine);

        // Medicine reactions
        lisinopril.getAdverseReactions().add(cough);
        ibuprofen.getAdverseReactions().add(stomachIrritation);

        // Treatment inclusions
        bpManagement.getMedicines().add(lisinopril);
        painManagement.getMedicines().add(ibuprofen);
        painManagement.getMedicines().add(sumatriptan);

        // Patient conditions and treatments
        patient.getConditions().add(hypertension);
        patient.getPrescriptions().add(lisinopril);
        patient.getSymptoms().add(dizziness);
        patient.getDepartments().add(cardiology);

        // Save updated entities
        departmentService.updateDepartment(cardiology);
        departmentService.updateDepartment(neurology);
        departmentService.updateDepartment(orthopedics);

        conditionService.updateCondition(hypertension);
        conditionService.updateCondition(arthritis);
        conditionService.updateCondition(migraine);

        medicineService.updateMedicine(lisinopril);
        medicineService.updateMedicine(ibuprofen);
        medicineService.updateMedicine(sumatriptan);

        treatmentService.updateTreatment(bpManagement);
        treatmentService.updateTreatment(painManagement);

        patientService.updatePatient(patient);
    }

    private void printHealthcareKnowledgeGraph() {
        System.out.println("\nHealthcare Knowledge Graph:");
        System.out.println("============================");
        System.out.flush();

        // Print Departments and their relationships
        System.out.println("\nDepartments:");
        for (Department dept : departmentService.getAllDepartments()) {
            System.out.println("\nDepartment: " + dept.getName());
            System.out.println("Description: " + dept.getDescription());
            System.out.flush();

            if (!dept.getSpecializedConditions().isEmpty()) {
                System.out.println("Specialized Conditions:");
                dept.getSpecializedConditions().forEach(condition ->
                    System.out.println(" - " + condition.getName()));
                System.out.flush();
            }

            if (!dept.getProcedures().isEmpty()) {
                System.out.println("Procedures:");
                dept.getProcedures().forEach(procedure ->
                    System.out.println(" - " + procedure.getName()));
                System.out.flush();
            }
        }

        // Print Conditions and their relationships
        System.out.println("\nConditions:");
        for (Condition condition : conditionService.getAllConditions()) {
            System.out.println("\nCondition: " + condition.getName());
            System.out.println("Category: " + condition.getCategory());
            System.out.flush();

            if (!condition.getSymptoms().isEmpty()) {
                System.out.println("Symptoms:");
                condition.getSymptoms().forEach(symptom ->
                    System.out.println(" - " + symptom.getName()));
                System.out.flush();
            }
        }

        // Print Medicines and their relationships
        System.out.println("\nMedicines:");
        for (Medicine medicine : medicineService.getAllMedicines()) {
            System.out.println("\nMedicine: " + medicine.getName());
            System.out.println("Description: " + medicine.getDescription());
            System.out.flush();

            if (!medicine.getTreatedConditions().isEmpty()) {
                System.out.println("Treats Conditions:");
                medicine.getTreatedConditions().forEach(condition ->
                    System.out.println(" - " + condition.getName()));
                System.out.flush();
            }

            if (!medicine.getAdverseReactions().isEmpty()) {
                System.out.println("Possible Reactions:");
                medicine.getAdverseReactions().forEach(reaction ->
                    System.out.println(" - " + reaction.getName() + " (Severity: " + reaction.getSeverity() + ")"));
                System.out.flush();
            }
        }

        // Print Patients and their relationships
        System.out.println("\nPatients:");
        for (Patient patient : patientService.getAllPatients()) {
            System.out.println("\nPatient: " + patient.getName());
            System.out.println("Description: " + patient.getDescription());
            System.out.flush();

            if (!patient.getConditions().isEmpty()) {
                System.out.println("Conditions:");
                patient.getConditions().forEach(condition ->
                    System.out.println(" - " + condition.getName()));
                System.out.flush();
            }

            if (!patient.getPrescriptions().isEmpty()) {
                System.out.println("Prescribed Medicines:");
                patient.getPrescriptions().forEach(medicine ->
                    System.out.println(" - " + medicine.getName()));
                System.out.flush();
            }

            if (!patient.getSymptoms().isEmpty()) {
                System.out.println("Symptoms:");
                patient.getSymptoms().forEach(symptom ->
                    System.out.println(" - " + symptom.getName()));
                System.out.flush();
            }

            if (!patient.getDepartments().isEmpty()) {
                System.out.println("Referred to Departments:");
                patient.getDepartments().forEach(dept ->
                    System.out.println(" - " + dept.getName()));
                System.out.flush();
            }
        }

        System.out.println("\n--------------------------------");
        System.out.flush();
    }

    private Department createDepartment(String name, String description) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        return departmentService.createDepartment(department);
    }

    private Condition createCondition(String name, String description, String category) {
        Condition condition = new Condition();
        condition.setName(name);
        condition.setDescription(description);
        condition.setCategory(category);
        return conditionService.createCondition(condition);
    }

    private Medicine createMedicine(String name, String description) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setDescription(description);
        return medicineService.createMedicine(medicine);
    }

    private Symptom createSymptom(String name, String description) {
        Symptom symptom = new Symptom();
        symptom.setName(name);
        symptom.setDescription(description);
        return symptomService.createSymptom(symptom);
    }

    private Treatment createTreatment(String name, String description) {
        Treatment treatment = new Treatment();
        treatment.setName(name);
        treatment.setDescription(description);
        return treatmentService.createTreatment(treatment);
    }

    private Reaction createReaction(String name, String description, String severity) {
        Reaction reaction = new Reaction();
        reaction.setName(name);
        reaction.setDescription(description);
        reaction.setSeverity(severity);
        return reactionService.createReaction(reaction);
    }

    private Procedure createProcedure(String name, String description, String category) {
        Procedure procedure = new Procedure();
        procedure.setName(name);
        procedure.setDescription(description);
        procedure.setCategory(category);
        return procedureService.createProcedure(procedure);
    }

    private Diagnosis createDiagnosis(String name, String description, String type) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(name);
        diagnosis.setDescription(description);
        diagnosis.setType(type);
        return diagnosisService.createDiagnosis(diagnosis);
    }

    private Patient createPatient(String name, String description) {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setDescription(description);
        return patientService.createPatient(patient);
    }
}
