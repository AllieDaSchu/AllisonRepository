import java.io.File;
import java.io.*;
import java.util.ArrayList;

/**
 * A class that works to create and change a shopping cart.
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version April 9, 2023
 */

public class ShoppingCart {
    // ArrayList of products in cart
    private ArrayList<Product> savedProducts = new ArrayList<Product>();
    // FileName for the cart file
    private String fileName;

    // Creates a specialized fileName for the user
    public ShoppingCart(User user) {
        this(user.getIdentifier().replace(".", "-") + "Cart.txt");
    }

    // Reads the products in from the shopping cart file
    public ShoppingCart(String pFileName) {
        fileName = pFileName;
        File f = new File(fileName);
        if (!(f.exists())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        FileReader fr;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException fnfe) {
            return;
        }
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                Product product = productFromFileString(line);
                savedProducts.add(product);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteCartFile() {
        File f = new File(fileName);
        f.delete();
    }
    // Retrieves products in the cart
    public ArrayList<Product> getProducts() {
        return savedProducts;
    }

    // Empties the ArrayList and the file
    public void clearCart() {
        savedProducts.clear();
        this.writeCartFile();
    }

    // Writes to the Shopping Cart File
    public void writeCartFile() {
        try {
            File f = new File(fileName);
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < savedProducts.size(); i++) {
                pw.println(this.productAsFileString(savedProducts.get(i)));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Converts products from a product Class to a String
    public String productAsFileString (Product product) {
        return String.format("%s-%s-%s-%d-%.2f",product.getName(), product.getStoreName(),
                product.getDescription(), product.getQuantity(), product.getPrice());
    }

    // Converts products from a String to a product class
    public Product productFromFileString (String productString) {
        String[] productInfoSplit = productString.split("-");
        Product product = new Product(productInfoSplit[0], productInfoSplit[1], productInfoSplit[2],
                Integer.parseInt(productInfoSplit[3]), Double.parseDouble(productInfoSplit[4]));
        return product;
    }

    // Prints the shopping cart to the terminal
    public boolean printCart() {
        if (savedProducts.size() == 0) {
            System.out.println();
            System.out.println("User shopping cart is empty");
            return false;
        } else {
            System.out.println();
            for (int i = 0; i < savedProducts.size(); i++) {
                String productName = savedProducts.get(i).getName();
                String storeName = savedProducts.get(i).getStoreName();
                String description = savedProducts.get(i).getDescription();
                int quantity = savedProducts.get(i).getQuantity();
                double price = savedProducts.get(i).getPrice();
                System.out.printf("%d - Product: %s  Store: %s  Description: %s  Quantity: %d  Price: %.2f",
                        i + 1, productName, storeName, description, quantity, price);
                System.out.println();
            }
            return true;
        }
    }

    // Adds a product to the ArrayList and writes to Cart File
    public void addProduct(Product product) {
        savedProducts.add(product);
        this.writeCartFile();
    }

    // Removes a product from the ArrayList and writes to Cart File
    public void removeProduct(int productIndex) {
        savedProducts.remove(productIndex - 1);
        this.writeCartFile();
    }

}