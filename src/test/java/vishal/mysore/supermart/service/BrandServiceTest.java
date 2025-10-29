package vishal.mysore.supermart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.supermart.model.Brand;
import vishal.mysore.supermart.repository.BrandRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Supermarket - Brand Service Tests")
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private Brand testBrand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBrand = new Brand();
        testBrand.setId(1L);
        testBrand.setName("Nike");
        testBrand.setDescription("Athletic footwear and apparel");
    }

    @Test
    @DisplayName("Should create brand successfully")
    void testCreateBrand() {
        when(brandRepository.save(any(Brand.class))).thenReturn(testBrand);

        Brand createdBrand = brandService.createBrand(testBrand);

        assertNotNull(createdBrand);
        assertEquals("Nike", createdBrand.getName());
        verify(brandRepository, times(1)).save(testBrand);
    }

    @Test
    @DisplayName("Should retrieve all brands")
    void testGetAllBrands() {
        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("Adidas");

        List<Brand> brands = Arrays.asList(testBrand, brand2);
        when(brandRepository.findAll()).thenReturn(brands);

        List<Brand> retrievedBrands = brandService.getAllBrands();

        assertNotNull(retrievedBrands);
        assertEquals(2, retrievedBrands.size());
        assertEquals("Nike", retrievedBrands.get(0).getName());
        assertEquals("Adidas", retrievedBrands.get(1).getName());
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get brand by name")
    void testGetBrandByName() {
        when(brandRepository.findByName("Nike")).thenReturn(testBrand);

        Brand result = brandService.getBrandByName("Nike");

        assertNotNull(result);
        assertEquals("Nike", result.getName());
        verify(brandRepository, times(1)).findByName("Nike");
    }

    @Test
    @DisplayName("Should update brand successfully")
    void testUpdateBrand() {
        testBrand.setDescription("Leading athletic and lifestyle brand worldwide");
        when(brandRepository.save(any(Brand.class))).thenReturn(testBrand);

        Brand updatedBrand = brandService.updateBrand(testBrand);

        assertNotNull(updatedBrand);
        assertTrue(updatedBrand.getDescription().contains("worldwide"));
        verify(brandRepository, times(1)).save(testBrand);
    }

    @Test
    @DisplayName("Should delete brand by id")
    void testDeleteBrand() {
        brandService.deleteBrand(1L);

        verify(brandRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty list when no brands found")
    void testGetAllBrandsEmpty() {
        when(brandRepository.findAll()).thenReturn(Arrays.asList());

        List<Brand> brands = brandService.getAllBrands();

        assertNotNull(brands);
        assertTrue(brands.isEmpty());
        verify(brandRepository, times(1)).findAll();
    }
}

