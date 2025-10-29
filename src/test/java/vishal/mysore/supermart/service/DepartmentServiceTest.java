package vishal.mysore.supermart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.supermart.model.Department;
import vishal.mysore.supermart.repository.DepartmentRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Supermarket Department Service Tests")
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department testDepartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testDepartment = new Department();
        testDepartment.setId(1L);
        testDepartment.setName("Grocery");
        testDepartment.setDescription("Food and household essentials");
    }

    @Test
    @DisplayName("Should create department successfully")
    void testCreateDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

        Department createdDepartment = departmentService.createDepartment(testDepartment);

        assertNotNull(createdDepartment);
        assertEquals("Grocery", createdDepartment.getName());
        assertEquals("Food and household essentials", createdDepartment.getDescription());
        verify(departmentRepository, times(1)).save(testDepartment);
    }

    @Test
    @DisplayName("Should retrieve all departments")
    void testGetAllDepartments() {
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("Electronics");
        department2.setDescription("Consumer electronics and gadgets");

        List<Department> departments = Arrays.asList(testDepartment, department2);
        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> retrievedDepartments = departmentService.getAllDepartments();

        assertNotNull(retrievedDepartments);
        assertEquals(2, retrievedDepartments.size());
        assertEquals("Grocery", retrievedDepartments.get(0).getName());
        assertEquals("Electronics", retrievedDepartments.get(1).getName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get department by name")
    void testGetDepartmentByName() {
        when(departmentRepository.findByName("Grocery")).thenReturn(testDepartment);

        Department result = departmentService.getDepartmentByName("Grocery");

        assertNotNull(result);
        assertEquals("Grocery", result.getName());
        verify(departmentRepository, times(1)).findByName("Grocery");
    }

    @Test
    @DisplayName("Should update department successfully")
    void testUpdateDepartment() {
        testDepartment.setDescription("Food, household and personal care");
        when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

        Department updatedDepartment = departmentService.updateDepartment(testDepartment);

        assertNotNull(updatedDepartment);
        assertEquals("Food, household and personal care", updatedDepartment.getDescription());
        verify(departmentRepository, times(1)).save(testDepartment);
    }

    @Test
    @DisplayName("Should delete department by id")
    void testDeleteDepartment() {
        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate department attributes")
    void testDepartmentAttributes() {
        assertEquals(1L, testDepartment.getId());
        assertEquals("Grocery", testDepartment.getName());
        assertEquals("Food and household essentials", testDepartment.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no departments found")
    void testGetAllDepartmentsEmpty() {
        when(departmentRepository.findAll()).thenReturn(Arrays.asList());

        List<Department> departments = departmentService.getAllDepartments();

        assertNotNull(departments);
        assertTrue(departments.isEmpty());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when department not found")
    void testGetDepartmentByNameNotFound() {
        when(departmentRepository.findByName("NonExistent")).thenReturn(null);

        Department result = departmentService.getDepartmentByName("NonExistent");

        assertNull(result);
        verify(departmentRepository, times(1)).findByName("NonExistent");
    }
}

