package vishal.mysore.hc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.hc.model.Diagnosis;
import vishal.mysore.hc.repository.DiagnosisRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Healthcare - Diagnosis Service Tests")
class DiagnosisServiceTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @InjectMocks
    private DiagnosisService diagnosisService;

    private Diagnosis testDiagnosis;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testDiagnosis = new Diagnosis();
        testDiagnosis.setId(1L);
        testDiagnosis.setName("Hypertension");
        testDiagnosis.setDescription("High blood pressure condition");
    }

    @Test
    @DisplayName("Should create diagnosis successfully")
    void testCreateDiagnosis() {
        when(diagnosisRepository.save(any(Diagnosis.class))).thenReturn(testDiagnosis);

        Diagnosis createdDiagnosis = diagnosisService.createDiagnosis(testDiagnosis);

        assertNotNull(createdDiagnosis);
        assertEquals("Hypertension", createdDiagnosis.getName());
        verify(diagnosisRepository, times(1)).save(testDiagnosis);
    }

    @Test
    @DisplayName("Should retrieve all diagnoses")
    void testGetAllDiagnoses() {
        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setId(2L);
        diagnosis2.setName("Diabetes");
        diagnosis2.setDescription("Blood sugar disorder");

        List<Diagnosis> diagnoses = Arrays.asList(testDiagnosis, diagnosis2);
        when(diagnosisRepository.findAll()).thenReturn(diagnoses);

        List<Diagnosis> retrievedDiagnoses = diagnosisService.getAllDiagnoses();

        assertNotNull(retrievedDiagnoses);
        assertEquals(2, retrievedDiagnoses.size());
        assertEquals("Hypertension", retrievedDiagnoses.get(0).getName());
        assertEquals("Diabetes", retrievedDiagnoses.get(1).getName());
        verify(diagnosisRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get diagnosis by name")
    void testGetDiagnosisByName() {
        when(diagnosisRepository.findByName("Hypertension")).thenReturn(testDiagnosis);

        Diagnosis result = diagnosisService.getDiagnosisByName("Hypertension");

        assertNotNull(result);
        assertEquals("Hypertension", result.getName());
        verify(diagnosisRepository, times(1)).findByName("Hypertension");
    }

    @Test
    @DisplayName("Should update diagnosis successfully")
    void testUpdateDiagnosis() {
        testDiagnosis.setDescription("Chronic high blood pressure condition requiring management");
        when(diagnosisRepository.save(any(Diagnosis.class))).thenReturn(testDiagnosis);

        Diagnosis updatedDiagnosis = diagnosisService.updateDiagnosis(testDiagnosis);

        assertNotNull(updatedDiagnosis);
        assertTrue(updatedDiagnosis.getDescription().contains("Chronic"));
        verify(diagnosisRepository, times(1)).save(testDiagnosis);
    }

    @Test
    @DisplayName("Should delete diagnosis by id")
    void testDeleteDiagnosis() {
        diagnosisService.deleteDiagnosis(1L);

        verify(diagnosisRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate diagnosis attributes")
    void testDiagnosisAttributes() {
        assertEquals(1L, testDiagnosis.getId());
        assertEquals("Hypertension", testDiagnosis.getName());
        assertEquals("High blood pressure condition", testDiagnosis.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no diagnoses found")
    void testGetAllDiagnosesEmpty() {
        when(diagnosisRepository.findAll()).thenReturn(Arrays.asList());

        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnoses();

        assertNotNull(diagnoses);
        assertTrue(diagnoses.isEmpty());
        verify(diagnosisRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when diagnosis not found")
    void testGetDiagnosisByNameNotFound() {
        when(diagnosisRepository.findByName("NonExistent")).thenReturn(null);

        Diagnosis result = diagnosisService.getDiagnosisByName("NonExistent");

        assertNull(result);
        verify(diagnosisRepository, times(1)).findByName("NonExistent");
    }
}

