package vishal.mysore.fd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.fd.model.PreventionMethod;
import vishal.mysore.fd.repository.PreventionMethodRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Fraud Detection - Prevention Method Service Tests")
class PreventionMethodServiceTest {

    @Mock
    private PreventionMethodRepository preventionMethodRepository;

    @InjectMocks
    private PreventionMethodService preventionMethodService;

    private PreventionMethod testPreventionMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPreventionMethod = new PreventionMethod();
        testPreventionMethod.setId(1L);
        testPreventionMethod.setName("Two-Factor Authentication");
        testPreventionMethod.setDescription("2FA security measure for account protection");
    }

    @Test
    @DisplayName("Should create prevention method successfully")
    void testCreatePreventionMethod() {
        when(preventionMethodRepository.save(any(PreventionMethod.class))).thenReturn(testPreventionMethod);

        PreventionMethod createdMethod = preventionMethodService.createPreventionMethod(testPreventionMethod);

        assertNotNull(createdMethod);
        assertEquals("Two-Factor Authentication", createdMethod.getName());
        verify(preventionMethodRepository, times(1)).save(testPreventionMethod);
    }

    @Test
    @DisplayName("Should retrieve all prevention methods")
    void testGetAllPreventionMethods() {
        PreventionMethod method2 = new PreventionMethod();
        method2.setId(2L);
        method2.setName("Encryption");
        method2.setDescription("Data encryption for protection");

        List<PreventionMethod> methods = Arrays.asList(testPreventionMethod, method2);
        when(preventionMethodRepository.findAll()).thenReturn(methods);

        List<PreventionMethod> retrievedMethods = preventionMethodService.getAllPreventionMethods();

        assertNotNull(retrievedMethods);
        assertEquals(2, retrievedMethods.size());
        assertEquals("Two-Factor Authentication", retrievedMethods.get(0).getName());
        assertEquals("Encryption", retrievedMethods.get(1).getName());
        verify(preventionMethodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get prevention method by name")
    void testGetPreventionMethodByName() {
        when(preventionMethodRepository.findByName("Two-Factor Authentication")).thenReturn(testPreventionMethod);

        PreventionMethod result = preventionMethodService.getPreventionMethodByName("Two-Factor Authentication");

        assertNotNull(result);
        assertEquals("Two-Factor Authentication", result.getName());
        verify(preventionMethodRepository, times(1)).findByName("Two-Factor Authentication");
    }

    @Test
    @DisplayName("Should update prevention method successfully")
    void testUpdatePreventionMethod() {
        testPreventionMethod.setDescription("Advanced 2FA security with biometric verification");
        when(preventionMethodRepository.save(any(PreventionMethod.class))).thenReturn(testPreventionMethod);

        PreventionMethod updatedMethod = preventionMethodService.updatePreventionMethod(testPreventionMethod);

        assertNotNull(updatedMethod);
        assertTrue(updatedMethod.getDescription().contains("biometric"));
        verify(preventionMethodRepository, times(1)).save(testPreventionMethod);
    }

    @Test
    @DisplayName("Should delete prevention method by id")
    void testDeletePreventionMethod() {
        preventionMethodService.deletePreventionMethod(1L);

        verify(preventionMethodRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate prevention method attributes")
    void testPreventionMethodAttributes() {
        assertEquals(1L, testPreventionMethod.getId());
        assertEquals("Two-Factor Authentication", testPreventionMethod.getName());
        assertEquals("2FA security measure for account protection", testPreventionMethod.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no prevention methods found")
    void testGetAllPreventionMethodsEmpty() {
        when(preventionMethodRepository.findAll()).thenReturn(Arrays.asList());

        List<PreventionMethod> methods = preventionMethodService.getAllPreventionMethods();

        assertNotNull(methods);
        assertTrue(methods.isEmpty());
        verify(preventionMethodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when prevention method not found")
    void testGetPreventionMethodByNameNotFound() {
        when(preventionMethodRepository.findByName("NonExistent")).thenReturn(null);

        PreventionMethod result = preventionMethodService.getPreventionMethodByName("NonExistent");

        assertNull(result);
        verify(preventionMethodRepository, times(1)).findByName("NonExistent");
    }
}

