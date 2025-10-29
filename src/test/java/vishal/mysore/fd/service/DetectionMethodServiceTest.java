package vishal.mysore.fd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.fd.model.DetectionMethod;
import vishal.mysore.fd.repository.DetectionMethodRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Fraud Detection - Detection Method Service Tests")
class DetectionMethodServiceTest {

    @Mock
    private DetectionMethodRepository detectionMethodRepository;

    @InjectMocks
    private DetectionMethodService detectionMethodService;

    private DetectionMethod testDetectionMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testDetectionMethod = new DetectionMethod();
        testDetectionMethod.setId(1L);
        testDetectionMethod.setName("Machine Learning Analysis");
        testDetectionMethod.setDescription("ML-based fraud pattern detection");
    }

    @Test
    @DisplayName("Should create detection method successfully")
    void testCreateDetectionMethod() {
        when(detectionMethodRepository.save(any(DetectionMethod.class))).thenReturn(testDetectionMethod);

        DetectionMethod createdMethod = detectionMethodService.createDetectionMethod(testDetectionMethod);

        assertNotNull(createdMethod);
        assertEquals("Machine Learning Analysis", createdMethod.getName());
        verify(detectionMethodRepository, times(1)).save(testDetectionMethod);
    }

    @Test
    @DisplayName("Should retrieve all detection methods")
    void testGetAllDetectionMethods() {
        DetectionMethod method2 = new DetectionMethod();
        method2.setId(2L);
        method2.setName("Rule-Based Detection");
        method2.setDescription("Rule-based fraud detection system");

        List<DetectionMethod> methods = Arrays.asList(testDetectionMethod, method2);
        when(detectionMethodRepository.findAll()).thenReturn(methods);

        List<DetectionMethod> retrievedMethods = detectionMethodService.getAllDetectionMethods();

        assertNotNull(retrievedMethods);
        assertEquals(2, retrievedMethods.size());
        assertEquals("Machine Learning Analysis", retrievedMethods.get(0).getName());
        assertEquals("Rule-Based Detection", retrievedMethods.get(1).getName());
        verify(detectionMethodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get detection method by name")
    void testGetDetectionMethodByName() {
        when(detectionMethodRepository.findByName("Machine Learning Analysis")).thenReturn(testDetectionMethod);

        DetectionMethod result = detectionMethodService.getDetectionMethodByName("Machine Learning Analysis");

        assertNotNull(result);
        assertEquals("Machine Learning Analysis", result.getName());
        verify(detectionMethodRepository, times(1)).findByName("Machine Learning Analysis");
    }

    @Test
    @DisplayName("Should update detection method successfully")
    void testUpdateDetectionMethod() {
        testDetectionMethod.setDescription("Advanced ML-based fraud pattern detection with real-time analysis");
        when(detectionMethodRepository.save(any(DetectionMethod.class))).thenReturn(testDetectionMethod);

        DetectionMethod updatedMethod = detectionMethodService.updateDetectionMethod(testDetectionMethod);

        assertNotNull(updatedMethod);
        assertTrue(updatedMethod.getDescription().contains("real-time"));
        verify(detectionMethodRepository, times(1)).save(testDetectionMethod);
    }

    @Test
    @DisplayName("Should delete detection method by id")
    void testDeleteDetectionMethod() {
        detectionMethodService.deleteDetectionMethod(1L);

        verify(detectionMethodRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate detection method attributes")
    void testDetectionMethodAttributes() {
        assertEquals(1L, testDetectionMethod.getId());
        assertEquals("Machine Learning Analysis", testDetectionMethod.getName());
        assertEquals("ML-based fraud pattern detection", testDetectionMethod.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no detection methods found")
    void testGetAllDetectionMethodsEmpty() {
        when(detectionMethodRepository.findAll()).thenReturn(Arrays.asList());

        List<DetectionMethod> methods = detectionMethodService.getAllDetectionMethods();

        assertNotNull(methods);
        assertTrue(methods.isEmpty());
        verify(detectionMethodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when detection method not found")
    void testGetDetectionMethodByNameNotFound() {
        when(detectionMethodRepository.findByName("NonExistent")).thenReturn(null);

        DetectionMethod result = detectionMethodService.getDetectionMethodByName("NonExistent");

        assertNull(result);
        verify(detectionMethodRepository, times(1)).findByName("NonExistent");
    }
}

