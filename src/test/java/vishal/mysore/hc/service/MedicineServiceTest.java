package vishal.mysore.hc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.hc.model.Medicine;
import vishal.mysore.hc.repository.MedicineRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Healthcare - Medicine Service Tests")
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineService medicineService;

    private Medicine testMedicine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testMedicine = new Medicine();
        testMedicine.setId(1L);
        testMedicine.setName("Aspirin");
        testMedicine.setDescription("Pain reliever and fever reducer");
    }

    @Test
    @DisplayName("Should create medicine successfully")
    void testCreateMedicine() {
        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine);

        Medicine createdMedicine = medicineService.createMedicine(testMedicine);

        assertNotNull(createdMedicine);
        assertEquals("Aspirin", createdMedicine.getName());
        assertEquals("Pain reliever and fever reducer", createdMedicine.getDescription());
        verify(medicineRepository, times(1)).save(testMedicine);
    }

    @Test
    @DisplayName("Should retrieve all medicines")
    void testGetAllMedicines() {
        Medicine medicine2 = new Medicine();
        medicine2.setId(2L);
        medicine2.setName("Ibuprofen");
        medicine2.setDescription("Anti-inflammatory pain reliever");

        List<Medicine> medicines = Arrays.asList(testMedicine, medicine2);
        when(medicineRepository.findAll()).thenReturn(medicines);

        List<Medicine> retrievedMedicines = medicineService.getAllMedicines();

        assertNotNull(retrievedMedicines);
        assertEquals(2, retrievedMedicines.size());
        assertEquals("Aspirin", retrievedMedicines.get(0).getName());
        assertEquals("Ibuprofen", retrievedMedicines.get(1).getName());
        verify(medicineRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get medicine by name")
    void testGetMedicineByName() {
        when(medicineRepository.findByName("Aspirin")).thenReturn(testMedicine);

        Medicine result = medicineService.getMedicineByName("Aspirin");

        assertNotNull(result);
        assertEquals("Aspirin", result.getName());
        verify(medicineRepository, times(1)).findByName("Aspirin");
    }

    @Test
    @DisplayName("Should update medicine successfully")
    void testUpdateMedicine() {
        testMedicine.setDescription("Enhanced pain reliever and fever reducer");
        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine);

        Medicine updatedMedicine = medicineService.updateMedicine(testMedicine);

        assertNotNull(updatedMedicine);
        assertTrue(updatedMedicine.getDescription().contains("Enhanced"));
        verify(medicineRepository, times(1)).save(testMedicine);
    }

    @Test
    @DisplayName("Should delete medicine by id")
    void testDeleteMedicine() {
        medicineService.deleteMedicine(1L);

        verify(medicineRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate medicine attributes")
    void testMedicineAttributes() {
        assertEquals(1L, testMedicine.getId());
        assertEquals("Aspirin", testMedicine.getName());
        assertEquals("Pain reliever and fever reducer", testMedicine.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no medicines found")
    void testGetAllMedicinesEmpty() {
        when(medicineRepository.findAll()).thenReturn(Arrays.asList());

        List<Medicine> medicines = medicineService.getAllMedicines();

        assertNotNull(medicines);
        assertTrue(medicines.isEmpty());
        verify(medicineRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when medicine not found")
    void testGetMedicineByNameNotFound() {
        when(medicineRepository.findByName("NonExistent")).thenReturn(null);

        Medicine result = medicineService.getMedicineByName("NonExistent");

        assertNull(result);
        verify(medicineRepository, times(1)).findByName("NonExistent");
    }
}

