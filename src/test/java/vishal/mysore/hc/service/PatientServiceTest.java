package vishal.mysore.hc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.hc.model.Patient;
import vishal.mysore.hc.repository.PatientRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Healthcare - Patient Service Tests")
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setName("Alice Johnson");
        testPatient.setDescription("Regular patient with hypertension");
    }

    @Test
    @DisplayName("Should create patient successfully")
    void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        Patient createdPatient = patientService.createPatient(testPatient);

        assertNotNull(createdPatient);
        assertEquals("Alice Johnson", createdPatient.getName());
        assertEquals("Regular patient with hypertension", createdPatient.getDescription());
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("Should retrieve all patients")
    void testGetAllPatients() {
        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Bob Smith");
        patient2.setDescription("Patient with diabetes");

        List<Patient> patients = Arrays.asList(testPatient, patient2);
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> retrievedPatients = patientService.getAllPatients();

        assertNotNull(retrievedPatients);
        assertEquals(2, retrievedPatients.size());
        assertEquals("Alice Johnson", retrievedPatients.get(0).getName());
        assertEquals("Bob Smith", retrievedPatients.get(1).getName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get patient by name")
    void testGetPatientByName() {
        when(patientRepository.findByName("Alice Johnson")).thenReturn(testPatient);

        Patient result = patientService.getPatientByName("Alice Johnson");

        assertNotNull(result);
        assertEquals("Alice Johnson", result.getName());
        verify(patientRepository, times(1)).findByName("Alice Johnson");
    }

    @Test
    @DisplayName("Should update patient successfully")
    void testUpdatePatient() {
        testPatient.setDescription("Patient with hypertension and diabetes");
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        Patient updatedPatient = patientService.updatePatient(testPatient);

        assertNotNull(updatedPatient);
        assertTrue(updatedPatient.getDescription().contains("diabetes"));
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("Should delete patient by id")
    void testDeletePatient() {
        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate patient attributes")
    void testPatientAttributes() {
        assertEquals(1L, testPatient.getId());
        assertEquals("Alice Johnson", testPatient.getName());
        assertEquals("Regular patient with hypertension", testPatient.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no patients found")
    void testGetAllPatientsEmpty() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList());

        List<Patient> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertTrue(patients.isEmpty());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when patient not found")
    void testGetPatientByNameNotFound() {
        when(patientRepository.findByName("NonExistent")).thenReturn(null);

        Patient result = patientService.getPatientByName("NonExistent");

        assertNull(result);
        verify(patientRepository, times(1)).findByName("NonExistent");
    }
}

