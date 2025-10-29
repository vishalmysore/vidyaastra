package vishal.mysore.yoga.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.yoga.model.YogaBenefit;
import vishal.mysore.yoga.repository.YogaBenefitRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Yoga Benefit Service Tests")
class YogaBenefitServiceTest {

    @Mock
    private YogaBenefitRepository yogaBenefitRepository;

    @InjectMocks
    private YogaBenefitService yogaBenefitService;

    private YogaBenefit testBenefit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBenefit = new YogaBenefit();
        testBenefit.setId(1L);
        testBenefit.setName("Flexibility");
        testBenefit.setDescription("Improves body flexibility and range of motion");
        testBenefit.setCategory("Physical");
    }

    @Test
    @DisplayName("Should create yoga benefit successfully")
    void testCreateYogaBenefit() {
        when(yogaBenefitRepository.save(any(YogaBenefit.class))).thenReturn(testBenefit);

        YogaBenefit createdBenefit = yogaBenefitService.createYogaBenefit(testBenefit);

        assertNotNull(createdBenefit);
        assertEquals("Flexibility", createdBenefit.getName());
        assertEquals("Physical", createdBenefit.getCategory());
        verify(yogaBenefitRepository, times(1)).save(testBenefit);
    }

    @Test
    @DisplayName("Should retrieve all yoga benefits")
    void testGetAllYogaBenefits() {
        YogaBenefit benefit2 = new YogaBenefit();
        benefit2.setId(2L);
        benefit2.setName("Stress Relief");
        benefit2.setCategory("Mental");

        List<YogaBenefit> benefits = Arrays.asList(testBenefit, benefit2);
        when(yogaBenefitRepository.findAll()).thenReturn(benefits);

        List<YogaBenefit> retrievedBenefits = yogaBenefitService.getAllYogaBenefits();

        assertNotNull(retrievedBenefits);
        assertEquals(2, retrievedBenefits.size());
        assertEquals("Flexibility", retrievedBenefits.get(0).getName());
        assertEquals("Stress Relief", retrievedBenefits.get(1).getName());
        verify(yogaBenefitRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get yoga benefit by id")
    void testGetYogaBenefitById() {
        when(yogaBenefitRepository.findById(1L)).thenReturn(Optional.of(testBenefit));

        Optional<YogaBenefit> result = yogaBenefitService.getYogaBenefitById(1L);

        assertTrue(result.isPresent());
        assertEquals("Flexibility", result.get().getName());
        verify(yogaBenefitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should update yoga benefit successfully")
    void testUpdateYogaBenefit() {
        testBenefit.setDescription("Significantly improves flexibility and joint health");
        when(yogaBenefitRepository.save(any(YogaBenefit.class))).thenReturn(testBenefit);

        YogaBenefit updatedBenefit = yogaBenefitService.updateYogaBenefit(testBenefit);

        assertNotNull(updatedBenefit);
        assertEquals("Significantly improves flexibility and joint health", updatedBenefit.getDescription());
        verify(yogaBenefitRepository, times(1)).save(testBenefit);
    }

    @Test
    @DisplayName("Should delete yoga benefit by id")
    void testDeleteYogaBenefit() {
        yogaBenefitService.deleteYogaBenefit(1L);

        verify(yogaBenefitRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate yoga benefit attributes")
    void testYogaBenefitAttributes() {
        assertEquals(1L, testBenefit.getId());
        assertEquals("Flexibility", testBenefit.getName());
        assertEquals("Improves body flexibility and range of motion", testBenefit.getDescription());
        assertEquals("Physical", testBenefit.getCategory());
    }

    @Test
    @DisplayName("Should return empty list when no yoga benefits found")
    void testGetAllYogaBenefitsEmpty() {
        when(yogaBenefitRepository.findAll()).thenReturn(Arrays.asList());

        List<YogaBenefit> benefits = yogaBenefitService.getAllYogaBenefits();

        assertNotNull(benefits);
        assertTrue(benefits.isEmpty());
        verify(yogaBenefitRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty optional when yoga benefit not found by id")
    void testGetYogaBenefitByIdNotFound() {
        when(yogaBenefitRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<YogaBenefit> result = yogaBenefitService.getYogaBenefitById(999L);

        assertFalse(result.isPresent());
        verify(yogaBenefitRepository, times(1)).findById(999L);
    }
}

