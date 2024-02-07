import java.util.ArrayList;
import java.util.List;

public class Seller {
    private String name;
    private String storeName;
    private List<Product> products;

    public Seller(String name, String storeName) {
        this.name = name;
        this.storeName = storeName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
