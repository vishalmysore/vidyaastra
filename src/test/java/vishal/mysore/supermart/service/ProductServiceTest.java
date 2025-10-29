package vishal.mysore.supermart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.supermart.model.Product;
import vishal.mysore.supermart.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Supermarket Product Service Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Apple");
        testProduct.setDescription("Fresh red apples");
        testProduct.setPrice(1.99);
    }

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product createdProduct = productService.createProduct(testProduct);

        assertNotNull(createdProduct);
        assertEquals("Apple", createdProduct.getName());
        assertEquals(1.99, createdProduct.getPrice());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    @DisplayName("Should retrieve all products")
    void testGetAllProducts() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Banana");
        product2.setPrice(0.99);

        List<Product> products = Arrays.asList(testProduct, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        assertEquals("Apple", retrievedProducts.get(0).getName());
        assertEquals("Banana", retrievedProducts.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get product by name")
    void testGetProductByName() {
        when(productRepository.findByName("Apple")).thenReturn(testProduct);

        Product result = productService.getProductByName("Apple");

        assertNotNull(result);
        assertEquals("Apple", result.getName());
        assertEquals(1.99, result.getPrice());
        verify(productRepository, times(1)).findByName("Apple");
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct() {
        testProduct.setPrice(2.49);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product updatedProduct = productService.updateProduct(testProduct);

        assertNotNull(updatedProduct);
        assertEquals(2.49, updatedProduct.getPrice());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    @DisplayName("Should delete product by id")
    void testDeleteProduct() {
        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate product attributes")
    void testProductAttributes() {
        assertEquals(1L, testProduct.getId());
        assertEquals("Apple", testProduct.getName());
        assertEquals("Fresh red apples", testProduct.getDescription());
        assertEquals(1.99, testProduct.getPrice());
    }

    @Test
    @DisplayName("Should return empty list when no products found")
    void testGetAllProductsEmpty() {
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertTrue(products.isEmpty());
        verify(productRepository, times(1)).findAll();
    }
}

