package vishal.mysore.supermart.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;
import vishal.mysore.supermart.model.*;
import vishal.mysore.supermart.service.*;

@Component
public class SupermartDataLoader implements CommandLineRunner {
    private final CustomerService customerService;
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SupplierService supplierService;
    private final PromotionService promotionService;
    private final ServiceService serviceService;
    private final PaymentService paymentService;
    private final LocationService locationService;
    private final Neo4jClient neo4jClient;

    @Autowired
    public SupermartDataLoader(
            CustomerService customerService,
            DepartmentService departmentService,
            ProductService productService,
            CategoryService categoryService,
            BrandService brandService,
            SupplierService supplierService,
            PromotionService promotionService,
            ServiceService serviceService,
            PaymentService paymentService,
            LocationService locationService,
            Neo4jClient neo4jClient) {
        this.customerService = customerService;
        this.departmentService = departmentService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.supplierService = supplierService;
        this.promotionService = promotionService;
        this.serviceService = serviceService;
        this.paymentService = paymentService;
        this.locationService = locationService;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            clearDatabase();
            loadSupermartKnowledgeGraph();
            printSupermartKnowledgeGraph();
            System.out.println("\nData loading and printing completed successfully.");
            System.out.flush();
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Failed to load supermart data: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        System.out.println("Database cleared successfully");
    }

    private void loadSupermartKnowledgeGraph() {
        // Create Departments
        Department grocery = createDepartment(
            "Grocery",
            "Food and household essentials"
        );

        Department electronics = createDepartment(
            "Electronics",
            "Consumer electronics and gadgets"
        );

        Department clothing = createDepartment(
            "Clothing",
            "Apparel and accessories"
        );

        // Create Categories
        Category fruits = createCategory(
            "Fruits",
            "Fresh fruits and produce"
        );

        Category dairy = createCategory(
            "Dairy",
            "Milk and dairy products"
        );

        Category smartphones = createCategory(
            "Smartphones",
            "Mobile phones and accessories"
        );

        // Create Products
        Product apple = createProduct(
            "Apple",
            "Fresh red apples",
            1.99
        );

        Product milk = createProduct(
            "Milk",
            "Fresh dairy milk",
            3.99
        );

        Product iphone = createProduct(
            "iPhone",
            "Latest iPhone model",
            999.99
        );

        // Create Brands
        Brand freshdaily = createBrand(
            "Fresh Daily",
            "Fresh produce brand"
        );

        Brand dairyfarm = createBrand(
            "Dairy Farm",
            "Premium dairy products"
        );

        Brand apple_brand = createBrand(
            "Apple",
            "Consumer electronics manufacturer"
        );

        // Create Suppliers
        Supplier localFarm = createSupplier(
            "Local Farm",
            "Local produce supplier"
        );

        Supplier dairyco = createSupplier(
            "DairyCo",
            "Dairy products supplier"
        );

        Supplier techDist = createSupplier(
            "Tech Distributors",
            "Electronics distributor"
        );

        // Create Locations
        Location mainStore = createLocation(
            "Main Store",
            "Main retail location"
        );

        Location warehouse = createLocation(
            "Warehouse",
            "Storage and distribution center"
        );

        // Create Services
        Service delivery = createService(
            "Home Delivery",
            "Door-to-door delivery service"
        );

        Service customerService = createService(
            "Customer Service",
            "Customer support and assistance"
        );

        // Create Payment Methods
        Payment creditCard = createPayment(
            "Credit Card",
            "Credit card payment"
        );

        Payment cash = createPayment(
            "Cash",
            "Cash payment"
        );

        // Create Promotions
        Promotion discount = createPromotion(
            "Summer Sale",
            "Summer season discounts"
        );

        Promotion loyalty = createPromotion(
            "Loyalty Rewards",
            "Member loyalty program"
        );

        // Create Customer
        Customer customer = createCustomer(
            "John Smith",
            "Regular customer"
        );

        // Establish relationships

        // Department Categories
        grocery.getCategories().add(fruits);
        grocery.getCategories().add(dairy);
        electronics.getCategories().add(smartphones);

        // Product Categories
        apple.getCategories().add(fruits);
        milk.getCategories().add(dairy);
        iphone.getCategories().add(smartphones);

        // Product Brands
        apple.getBrands().add(freshdaily);
        milk.getBrands().add(dairyfarm);
        iphone.getBrands().add(apple_brand);

        // Department Products
        grocery.getProducts().add(apple);
        grocery.getProducts().add(milk);
        electronics.getProducts().add(iphone);

        // Supplier Relationships
        localFarm.getDepartments().add(grocery);
        dairyco.getDepartments().add(grocery);
        techDist.getDepartments().add(electronics);

        // Location Departments
        mainStore.getDepartments().add(grocery);
        mainStore.getDepartments().add(electronics);
        mainStore.getDepartments().add(clothing);
        warehouse.getDepartments().add(grocery);

        // Payment Methods
        mainStore.getAcceptedPayments().add(creditCard);
        mainStore.getAcceptedPayments().add(cash);

        // Customer Relationships
        customer.getDepartments().add(grocery);
        customer.getDepartments().add(electronics);
        customer.getPreferences().add(fruits);
        customer.getPurchases().add(apple);
        customer.getPurchases().add(iphone);
        customer.getServices().add(delivery);
        customer.getPaymentMethods().add(creditCard);

        // Save updated entities
        departmentService.updateDepartment(grocery);
        departmentService.updateDepartment(electronics);
        departmentService.updateDepartment(clothing);

        categoryService.updateCategory(fruits);
        categoryService.updateCategory(dairy);
        categoryService.updateCategory(smartphones);

        productService.updateProduct(apple);
        productService.updateProduct(milk);
        productService.updateProduct(iphone);

        brandService.updateBrand(freshdaily);
        brandService.updateBrand(dairyfarm);
        brandService.updateBrand(apple_brand);

        supplierService.updateSupplier(localFarm);
        supplierService.updateSupplier(dairyco);
        supplierService.updateSupplier(techDist);

        locationService.updateLocation(mainStore);
        locationService.updateLocation(warehouse);

        customerService.updateCustomer(customer);
    }

    private void printSupermartKnowledgeGraph() {
        System.out.println("\nSupermarket Knowledge Graph:");
        System.out.println("============================");
        System.out.flush();

        // Print Departments and their relationships
        System.out.println("\nDepartments:");
        for (Department dept : departmentService.getAllDepartments()) {
            System.out.println("\nDepartment: " + dept.getName());
            System.out.println("Description: " + dept.getDescription());
            System.out.flush();

            if (!dept.getCategories().isEmpty()) {
                System.out.println("Categories:");
                dept.getCategories().forEach(category ->
                    System.out.println(" - " + category.getName()));
                System.out.flush();
            }

            if (!dept.getProducts().isEmpty()) {
                System.out.println("Products:");
                dept.getProducts().forEach(product ->
                    System.out.println(" - " + product.getName() + " ($" + product.getPrice() + ")"));
                System.out.flush();
            }
        }

        // Print Products and their relationships
        System.out.println("\nProducts:");
        for (Product product : productService.getAllProducts()) {
            System.out.println("\nProduct: " + product.getName());
            System.out.println("Price: $" + product.getPrice());
            System.out.flush();

            if (!product.getCategories().isEmpty()) {
                System.out.println("Categories:");
                product.getCategories().forEach(category ->
                    System.out.println(" - " + category.getName()));
                System.out.flush();
            }

            if (!product.getBrands().isEmpty()) {
                System.out.println("Brands:");
                product.getBrands().forEach(brand ->
                    System.out.println(" - " + brand.getName()));
                System.out.flush();
            }
        }

        // Print Customers and their relationships
        System.out.println("\nCustomers:");
        for (Customer customer : customerService.getAllCustomers()) {
            System.out.println("\nCustomer: " + customer.getName());
            System.out.println("Description: " + customer.getDescription());
            System.out.flush();

            if (!customer.getPurchases().isEmpty()) {
                System.out.println("Purchased Products:");
                customer.getPurchases().forEach(product ->
                    System.out.println(" - " + product.getName()));
                System.out.flush();
            }

            if (!customer.getPreferences().isEmpty()) {
                System.out.println("Preferred Categories:");
                customer.getPreferences().forEach(category ->
                    System.out.println(" - " + category.getName()));
                System.out.flush();
            }

            if (!customer.getServices().isEmpty()) {
                System.out.println("Used Services:");
                customer.getServices().forEach(service ->
                    System.out.println(" - " + service.getName()));
                System.out.flush();
            }
        }

        System.out.println("\n--------------------------------");
        System.out.flush();
    }

    private Department createDepartment(String name, String description) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        return departmentService.createDepartment(department);
    }

    private Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryService.createCategory(category);
    }

    private Product createProduct(String name, String description, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return productService.createProduct(product);
    }

    private Brand createBrand(String name, String description) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(description);
        return brandService.createBrand(brand);
    }

    private Supplier createSupplier(String name, String description) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setDescription(description);
        return supplierService.createSupplier(supplier);
    }

    private Service createService(String name, String description) {
        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        return serviceService.createService(service);
    }

    private Payment createPayment(String name, String description) {
        Payment payment = new Payment();
        payment.setName(name);
        payment.setDescription(description);
        return paymentService.createPayment(payment);
    }

    private Location createLocation(String name, String description) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        return locationService.createLocation(location);
    }

    private Promotion createPromotion(String name, String description) {
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDescription(description);
        return promotionService.createPromotion(promotion);
    }

    private Customer createCustomer(String name, String description) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setDescription(description);
        return customerService.createCustomer(customer);
    }
}
