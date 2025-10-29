package vishal.mysore.fd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.fd.model.FraudType;
import vishal.mysore.fd.repository.FraudTypeRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Fraud Detection - Fraud Type Service Tests")
class FraudTypeServiceTest {

    @Mock
    private FraudTypeRepository fraudTypeRepository;

    @InjectMocks
    private FraudTypeService fraudTypeService;

    private FraudType testFraudType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testFraudType = new FraudType();
        testFraudType.setId(1L);
        testFraudType.setName("Identity Theft");
        testFraudType.setDescription("Unauthorized use of personal information");
        testFraudType.setCategory("Identity");
    }

    @Test
    @DisplayName("Should create fraud type successfully")
    void testCreateFraudType() {
        when(fraudTypeRepository.save(any(FraudType.class))).thenReturn(testFraudType);

        FraudType createdFraudType = fraudTypeService.createFraudType(testFraudType);

        assertNotNull(createdFraudType);
        assertEquals("Identity Theft", createdFraudType.getName());
        assertEquals("Identity", createdFraudType.getCategory());
        verify(fraudTypeRepository, times(1)).save(testFraudType);
    }

    @Test
    @DisplayName("Should retrieve all fraud types")
    void testGetAllFraudTypes() {
        FraudType fraudType2 = new FraudType();
        fraudType2.setId(2L);
        fraudType2.setName("Credit Card Fraud");
        fraudType2.setCategory("Financial");

        List<FraudType> fraudTypes = Arrays.asList(testFraudType, fraudType2);
        when(fraudTypeRepository.findAll()).thenReturn(fraudTypes);

        List<FraudType> retrievedFraudTypes = fraudTypeService.getAllFraudTypes();

        assertNotNull(retrievedFraudTypes);
        assertEquals(2, retrievedFraudTypes.size());
        assertEquals("Identity Theft", retrievedFraudTypes.get(0).getName());
        assertEquals("Credit Card Fraud", retrievedFraudTypes.get(1).getName());
        verify(fraudTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get fraud type by name")
    void testGetFraudTypeByName() {
        when(fraudTypeRepository.findByName("Identity Theft")).thenReturn(testFraudType);

        FraudType result = fraudTypeService.getFraudTypeByName("Identity Theft");

        assertNotNull(result);
        assertEquals("Identity Theft", result.getName());
        verify(fraudTypeRepository, times(1)).findByName("Identity Theft");
    }

    @Test
    @DisplayName("Should update fraud type successfully")
    void testUpdateFraudType() {
        testFraudType.setDescription("Unauthorized use of personal or financial information");
        when(fraudTypeRepository.save(any(FraudType.class))).thenReturn(testFraudType);

        FraudType updatedFraudType = fraudTypeService.updateFraudType(testFraudType);

        assertNotNull(updatedFraudType);
        assertEquals("Unauthorized use of personal or financial information", updatedFraudType.getDescription());
        verify(fraudTypeRepository, times(1)).save(testFraudType);
    }

    @Test
    @DisplayName("Should delete fraud type by id")
    void testDeleteFraudType() {
        fraudTypeService.deleteFraudType(1L);

        verify(fraudTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate fraud type attributes")
    void testFraudTypeAttributes() {
        assertEquals(1L, testFraudType.getId());
        assertEquals("Identity Theft", testFraudType.getName());
        assertEquals("Unauthorized use of personal information", testFraudType.getDescription());
        assertEquals("Identity", testFraudType.getCategory());
        assertNotNull(testFraudType.getDetectionMethods());
        assertNotNull(testFraudType.getIndicators());
        assertNotNull(testFraudType.getPreventionMethods());
    }

    @Test
    @DisplayName("Should return empty list when no fraud types found")
    void testGetAllFraudTypesEmpty() {
        when(fraudTypeRepository.findAll()).thenReturn(Arrays.asList());

        List<FraudType> fraudTypes = fraudTypeService.getAllFraudTypes();

        assertNotNull(fraudTypes);
        assertTrue(fraudTypes.isEmpty());
        verify(fraudTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when fraud type not found")
    void testGetFraudTypeByNameNotFound() {
        when(fraudTypeRepository.findByName("NonExistent")).thenReturn(null);

        FraudType result = fraudTypeService.getFraudTypeByName("NonExistent");

        assertNull(result);
        verify(fraudTypeRepository, times(1)).findByName("NonExistent");
    }
}

