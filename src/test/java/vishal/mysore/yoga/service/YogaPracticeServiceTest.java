package vishal.mysore.yoga.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.yoga.model.YogaPractice;
import vishal.mysore.yoga.repository.YogaPracticeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Yoga Practice Service Tests")
class YogaPracticeServiceTest {

    @Mock
    private YogaPracticeRepository yogaPracticeRepository;

    @InjectMocks
    private YogaPracticeService yogaPracticeService;

    private YogaPractice testPractice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPractice = new YogaPractice();
        testPractice.setId(1L);
        testPractice.setName("Hatha Yoga");
        testPractice.setDescription("Traditional yoga practice focusing on breath and movement");
        testPractice.setCategory("Beginner");
    }

    @Test
    @DisplayName("Should create yoga practice successfully")
    void testCreateYogaPractice() {
        when(yogaPracticeRepository.save(any(YogaPractice.class))).thenReturn(testPractice);

        YogaPractice createdPractice = yogaPracticeService.createYogaPractice(testPractice);

        assertNotNull(createdPractice);
        assertEquals("Hatha Yoga", createdPractice.getName());
        assertEquals("Beginner", createdPractice.getCategory());
        verify(yogaPracticeRepository, times(1)).save(testPractice);
    }

    @Test
    @DisplayName("Should retrieve all yoga practices")
    void testGetAllYogaPractices() {
        YogaPractice practice2 = new YogaPractice();
        practice2.setId(2L);
        practice2.setName("Vinyasa Yoga");
        practice2.setCategory("Intermediate");

        List<YogaPractice> practices = Arrays.asList(testPractice, practice2);
        when(yogaPracticeRepository.findAll()).thenReturn(practices);

        List<YogaPractice> retrievedPractices = yogaPracticeService.getAllYogaPractices();

        assertNotNull(retrievedPractices);
        assertEquals(2, retrievedPractices.size());
        assertEquals("Hatha Yoga", retrievedPractices.get(0).getName());
        assertEquals("Vinyasa Yoga", retrievedPractices.get(1).getName());
        verify(yogaPracticeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get yoga practice by id")
    void testGetYogaPracticeById() {
        when(yogaPracticeRepository.findById(1L)).thenReturn(Optional.of(testPractice));

        Optional<YogaPractice> result = yogaPracticeService.getYogaPracticeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Hatha Yoga", result.get().getName());
        verify(yogaPracticeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should update yoga practice successfully")
    void testUpdateYogaPractice() {
        testPractice.setCategory("Intermediate");
        when(yogaPracticeRepository.save(any(YogaPractice.class))).thenReturn(testPractice);

        YogaPractice updatedPractice = yogaPracticeService.updateYogaPractice(testPractice);

        assertNotNull(updatedPractice);
        assertEquals("Intermediate", updatedPractice.getCategory());
        verify(yogaPracticeRepository, times(1)).save(testPractice);
    }

    @Test
    @DisplayName("Should delete yoga practice by id")
    void testDeleteYogaPractice() {
        yogaPracticeService.deleteYogaPractice(1L);

        verify(yogaPracticeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate yoga practice attributes")
    void testYogaPracticeAttributes() {
        assertEquals(1L, testPractice.getId());
        assertEquals("Hatha Yoga", testPractice.getName());
        assertEquals("Traditional yoga practice focusing on breath and movement", testPractice.getDescription());
        assertEquals("Beginner", testPractice.getCategory());
    }

    @Test
    @DisplayName("Should return empty list when no yoga practices found")
    void testGetAllYogaPracticesEmpty() {
        when(yogaPracticeRepository.findAll()).thenReturn(Arrays.asList());

        List<YogaPractice> practices = yogaPracticeService.getAllYogaPractices();

        assertNotNull(practices);
        assertTrue(practices.isEmpty());
        verify(yogaPracticeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty optional when yoga practice not found by id")
    void testGetYogaPracticeByIdNotFound() {
        when(yogaPracticeRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<YogaPractice> result = yogaPracticeService.getYogaPracticeById(999L);

        assertFalse(result.isPresent());
        verify(yogaPracticeRepository, times(1)).findById(999L);
    }
}

