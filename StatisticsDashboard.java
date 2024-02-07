import java.util.*;
import java.util.stream.Collectors;

public class StatisticsDashboard {

    private List<Seller> sellers;
    private List<Customer> customers;
    private List<Order> orders;

    public StatisticsDashboard(List<Seller> sellers, List<Customer> customers, List<Order> orders) {
        this.sellers = sellers;
        this.customers = customers;
        this.orders = orders;
    }

    public void sellerDashboard(Seller seller) {
        Map<String, Integer> itemsSold = new HashMap<>();
        Map<String, Integer> customersStats = new HashMap<>();

        for (Order order : orders) {
            if (order.getProduct().getStoreName().equals(seller.getStoreName())){
                itemsSold.put(order.getProduct().getName(), itemsSold.getOrDefault(order.getProduct().getName(), 0) + order.getQuantity());
                customersStats.put(order.getCustomer().getName(), customersStats.getOrDefault(order.getCustomer().getName(), 0) + order.getQuantity());
            }
        }

        // Sort the results
        LinkedHashMap<String, Integer> sortedItemsSold = itemsSold.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        LinkedHashMap<String, Integer> sortedCustomersStats = customersStats.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Products sold: " + sortedItemsSold);
        System.out.println("Customers: " + sortedCustomersStats);
    }

    public void customerDashboard(Customer customer) {
        Map<String, Integer> storesByProductsSold = new HashMap<>();
        Map<String, Integer> storesByCustomerPurchases = new HashMap<>();

        for (Order order : orders) {
            if (order.getCustomer().equals(customer)) {
                storesByCustomerPurchases.put(order.getSeller().getStoreName(), storesByCustomerPurchases.getOrDefault(order.getSeller().getStoreName(), 0) + order.getQuantity());
            }
            storesByProductsSold.put(order.getSeller().getStoreName(), storesByProductsSold.getOrDefault(order.getSeller().getStoreName(), 0) + order.getQuantity());
        }

        // Sort the results
        LinkedHashMap<String, Integer> sortedStoresByProductsSold = storesByProductsSold.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        LinkedHashMap<String, Integer> sortedStoresByCustomerPurchases = storesByCustomerPurchases.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Stores by products sold: " + sortedStoresByProductsSold);
        System.out.println("Stores by customer purchases: " + sortedStoresByCustomerPurchases);
    }
}
