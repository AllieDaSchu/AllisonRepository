import java.io.Serializable;
import java.nio.channels.SelectableChannel;

/**
 * A class that works to create and change a product sold in a marketplace application.
 *
 * <p> Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version April 9, 2023
 */

public class Product implements Serializable {

    private String name; // the name of the product
    private String storeName; // the store that sells this product
    private String description; // the product description
    private int quantity; // the quantity of the product in stock
    private double price; // the price of the product

    /**
     * Product
     * <p>
     * constructor for the product class
     *
     * @param name        name of the product
     * @param storeName   name of the store
     * @param description description of the product
     * @param quantity    quantity of the product in stock
     * @param price       price of the product
     */
    public Product(String name, String storeName, String description, int quantity, double price) {
        this.name = name;
        this.storeName = storeName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;

    }

    /**
     * getName
     * <p>
     * returns the name of the product
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * getStoreName
     * <p>
     * returns the name of the store
     *
     * @return store name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * getDescription
     * <p>
     * returns the description of the store
     *
     * @return store description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getQuantity
     * <p>
     * returns the quantity of the product
     *
     * @return quantity of product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * getPrice
     * <p>
     * returns product price
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }


    /**
     * equals
     * <p>
     * equals method for products. compares name, store name, description, quantity, and price. checks if object is
     * a product or not before comparing fields
     *
     * @param productObject the 'product' object to compare to
     * @return
     */
    public boolean equals(Object productObject) {

        if (productObject instanceof Product) {

            Product temp = (Product) productObject;

            if (temp.getName().equals(name) && temp.getStoreName().equals(storeName) &&
                    temp.getDescription().equals(description) && temp.getQuantity() == quantity &&
                    temp.getPrice() == price) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * toString
     * <p>
     * returns a string representation of the product: name-storeName-description-quantity-price
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return String.format("%s-%s-%s-%d-%.2f", name, storeName, description, quantity, price);
    }
}