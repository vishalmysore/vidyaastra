package vishal.mysore.supermart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.supermart.model.Customer;
import vishal.mysore.supermart.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Supermarket Customer Service Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("John Smith");
        testCustomer.setDescription("Regular customer");
    }

    @Test
    @DisplayName("Should create customer successfully")
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer createdCustomer = customerService.createCustomer(testCustomer);

        assertNotNull(createdCustomer);
        assertEquals("John Smith", createdCustomer.getName());
        assertEquals("Regular customer", createdCustomer.getDescription());
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    @DisplayName("Should retrieve all customers")
    void testGetAllCustomers() {
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");
        customer2.setDescription("Premium customer");

        List<Customer> customers = Arrays.asList(testCustomer, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> retrievedCustomers = customerService.getAllCustomers();

        assertNotNull(retrievedCustomers);
        assertEquals(2, retrievedCustomers.size());
        assertEquals("John Smith", retrievedCustomers.get(0).getName());
        assertEquals("Jane Doe", retrievedCustomers.get(1).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get customer by name")
    void testGetCustomerByName() {
        when(customerRepository.findByName("John Smith")).thenReturn(testCustomer);

        Customer result = customerService.getCustomerByName("John Smith");

        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        verify(customerRepository, times(1)).findByName("John Smith");
    }

    @Test
    @DisplayName("Should update customer successfully")
    void testUpdateCustomer() {
        testCustomer.setDescription("VIP customer");
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer updatedCustomer = customerService.updateCustomer(testCustomer);

        assertNotNull(updatedCustomer);
        assertEquals("VIP customer", updatedCustomer.getDescription());
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    @DisplayName("Should delete customer by id")
    void testDeleteCustomer() {
        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should validate customer attributes")
    void testCustomerAttributes() {
        assertEquals(1L, testCustomer.getId());
        assertEquals("John Smith", testCustomer.getName());
        assertEquals("Regular customer", testCustomer.getDescription());
    }

    @Test
    @DisplayName("Should return empty list when no customers found")
    void testGetAllCustomersEmpty() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList());

        List<Customer> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertTrue(customers.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when customer not found")
    void testGetCustomerByNameNotFound() {
        when(customerRepository.findByName("NonExistent")).thenReturn(null);

        Customer result = customerService.getCustomerByName("NonExistent");

        assertNull(result);
        verify(customerRepository, times(1)).findByName("NonExistent");
    }
}

