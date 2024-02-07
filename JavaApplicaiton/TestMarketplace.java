import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMarketplace {
    public static void main(String[] args) {
        // Create sellers
        Seller seller1 = new Seller("John", "John's Store");
        Seller seller2 = new Seller("Alice", "Alice's Store");

        // Create products
        Product product1 = new Product("Product 1", "John's Store", "Description 1", 10, 100.0);
        Product product2 = new Product("Product 2", "John's Store", "Description 2", 5, 50.0);
        Product product3 = new Product("Product 3", "Alice's Store", "Description 3", 20, 200.0);


        // Add products to sellers
        seller1.addProduct(product1);
        seller1.addProduct(product2);
        seller2.addProduct(product3);

        // Create customers
        Customer customer1 = new Customer("Michael");
        Customer customer2 = new Customer("Sarah");

        // Create orders
        Order order1 = new Order(customer1, seller1, product1, 2);
        Order order2 = new Order(customer2, seller2, product3, 1);
        Order order3 = new Order(customer1, seller1, product2, 3);

        // Add orders to customers
        customer1.addOrder(order1);
        customer1.addOrder(order3);
        customer2.addOrder(order2);

        // Create list of sellers, customers, and orders
        List<Seller> sellers = new ArrayList<>(Arrays.asList(seller1, seller2));
        List<Customer> customers = new ArrayList<>(Arrays.asList(customer1, customer2));
        List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2, order3));

        // Create a StatisticsDashboard instance
        StatisticsDashboard dashboard = new StatisticsDashboard(sellers, customers, orders);

        // Display seller dashboard for seller1
        System.out.println("Seller Dashboard for " + seller1.getName() + ":");
        dashboard.sellerDashboard(seller1);
        System.out.println();

        // Display seller dashboard for seller2
        System.out.println("Seller Dashboard for " + seller2.getName() + ":");
        dashboard.sellerDashboard(seller2);
        System.out.println();

        // Display customer dashboard for customer1
        System.out.println("Customer Dashboard for " + customer1.getName() + ":");
        dashboard.customerDashboard(customer1);
        System.out.println();

        // Display customer dashboard for customer2
        System.out.println("Customer Dashboard for " + customer2.getName() + ":");
        dashboard.customerDashboard(customer2);
        System.out.println();
    }
}
