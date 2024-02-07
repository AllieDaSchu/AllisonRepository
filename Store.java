import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class that is used to handle stores operating within a marketplace application
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version April 9, 2023
 */
public class Store implements Serializable {

    private String name; // the name of the store
    private String seller; // the seller that created the store
    private ArrayList<Product> products = new ArrayList<Product>(); // arraylist of products in the store
    private FileReader fr;
    private BufferedReader bfr;
    private PrintWriter pw;
    private FileOutputStream fos;
    private File f = new File("StoreInfo.txt"); // a file storing the information of all stores
    private File fTwo = new File("MarketPlace.txt"); // a file storing the marketplace shown to the customer
    private File salesFile = new File("PurchaseInfo.txt"); // a file storing sales receipts for purchases

    /**
     * Store
     * <p>
     * An empty constructor for the store class
     */
    public Store() {
        // empty constructor
    }

    /**
     * Store
     * <p>
     * A constructor for the store that assigns it a seller
     *
     * @param seller // the seller associated with the store
     */
    public Store(String seller) {
        this.seller = seller;
    }

    /**
     * Store
     * <p>
     * A constructor for the store class that assigns a name, seller, and an arraylist of products to the store
     *
     * @param name     // the name of the store
     * @param seller   // the seller associated with the store
     * @param products // the list of products in the store
     */
    public Store(String name, String seller, ArrayList<Product> products) {
        this.name = name;
        this.seller = seller;
        this.products = products;
    }

    /**
     * Store
     * <p>
     * A constructor for the store class that assigns a name and a seller
     *
     * @param name   the name of the store
     * @param seller the seller associated with the store
     */
    public Store(String name, String seller) {
        this.name = name;
        this.seller = seller;
    }

    /**
     * addStore
     * <p>
     * Creates and adds a store to the StoreInfo file
     */
     public void addStore() {
        try {
            fos = new FileOutputStream(f, true);
            pw = new PrintWriter(fos);
            Store store = new Store(getName(), getSeller(), getProducts());

            pw.println(store);

            pw.close();

            // stores.add(new Store());

            System.out.println();
            System.out.println("Store created successfully");
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }


    /**
     * removeStore
     * <p>
     * Removes a store from the list/file if it exists. Prints out an error if it doesn't exist
     */
    public void removeStore() {

        ArrayList<Store> list = new ArrayList<Store>();
        ArrayList<Product> pr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                pr = new ArrayList<Product>();
                if (productList.equals("[]")) {

                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]), Double.parseDouble(divss[4]));

                        pr.add(prs);
                    }
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                }

                line = bfr.readLine();
            }
            bfr.close();

            int flag = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSeller().equals(seller)) {
                    list.remove(list.get(j));
                    break;
                }
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }

            pw.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    /**
     * getName
     * Returns store name
     *
     * @return name of the store
     */
    public String getName() {
        return name;
    }

    /**
     * getSeller
     * returns the store seller
     *
     * @return the seller of the store
     */
    public String getSeller() {
        return seller;
    }

    /**
     * getProducts
     * returns the list of products in the store
     *
     * @return an arraylist of the products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * sets the name of the store to a new value
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the seller of the store to a new value
     *
     * @param seller the new seller
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * sets the product list in the store to a new value (MAYBE GET RID OF THIS?)
     *
     * @param products the new product list
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * addProduct
     * <p>
     * adds a new product to a store that the seller owns. checks if the seller calling this method owns the store
     * that it is attempting to modify
     *
     * @param product   the product to add
     * @param loginUser the user who called the method
     */
    public void addProduct(Product product, User loginUser) {

        ArrayList<Store> list = new ArrayList<Store>();
        ArrayList<Product> pr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                pr = new ArrayList<Product>();
                if (productList.equals("[]")) {

                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]), Double.parseDouble(divss[4]));

                        pr.add(prs);
                    }
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                }

                line = bfr.readLine();
            }
            bfr.close();

            int flag = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getName().equals(product.getStoreName()) && list.get(j).getSeller().equals(loginUser.getIdentifier())) {
                    list.get(j).getProducts().add(product);
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                System.out.println();
                System.out.println("Store does not exist!");
                return;
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }


            pw.close();
            System.out.println();
            System.out.println("Product created successfully");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * removeProduct
     * <p>
     * removes a product from the store. checks to make sure that the user calling the function is the seller that
     * owns the store
     *
     * @param deleteProductName the name of the product to delete
     * @param loginUser         the user who calls the function
     */
    public void removeProduct(String deleteProductName, User loginUser) {
        ArrayList<Store> list = new ArrayList<Store>();
        ArrayList<Product> pr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                pr = new ArrayList<Product>();
                if (productList.equals("[]")) {

                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]), Double.parseDouble(divss[4]));

                        pr.add(prs);
                    }
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                }

                line = bfr.readLine();
            }
            bfr.close();

            int flag = 0;
            for (int j = 0; j < list.size(); j++) {
                for (int k = 0; k < list.get(j).getProducts().size(); k++) {
                    if (list.get(j).getProducts().get(k).getName().equals(deleteProductName) && list.get(j).getSeller().equals(loginUser.getIdentifier())) {
                        list.get(j).getProducts().remove(k);
                        flag = 1;
                    }
                }
            }

            if (flag == 0) {
                System.out.println();
                System.out.println("Product does not exist!");
                return;
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }

            pw.close();
            System.out.println();
            System.out.println("Product deleted successfully");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * editProduct
     * <p>
     * edits a specific product in a specific store. checks to make sure that the user calling the function is the seller
     * that owns the store
     *
     * @param editProductName       the name of the product to edit
     * @param editStoreName         the name of the store the product is in
     * @param newProductDescription the new description
     * @param newProductQuantity    the new quantity
     * @param newProductPrice       the new price
     * @param loginUser             the user calling the function
     */
    public void editProduct(String editProductName, String editStoreName, String newProductDescription, int newProductQuantity, double newProductPrice, User loginUser) {
        ArrayList<Store> list = new ArrayList<Store>();
        ArrayList<Product> pr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                pr = new ArrayList<Product>();
                if (productList.equals("[]")) {

                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);

                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]), Double.parseDouble(divss[4]));

                        pr.add(prs);
                    }
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);
                }

                line = bfr.readLine();
            }
            bfr.close();

            int flag = 1;
            for (int j = 0; j < list.size(); j++) {
                for (int k = 0; k < list.get(j).getProducts().size(); k++) {
                    if (list.get(j).getProducts().get(k).getName().equals(editProductName) && list.get(j).getName().equals(editStoreName) && list.get(j).getSeller().equals(loginUser.getIdentifier())) {
                        list.get(j).getProducts().set(k, new Product(editProductName, editStoreName, newProductDescription, newProductQuantity, newProductPrice));
                        flag = 0;
                    }
                }
            }

            if (flag == 1) {
                System.out.println();
                System.out.println("Product or Store Name does not exist!");
                return;
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }


            pw.close();
            System.out.println();
            System.out.println("Product edited successfully");
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    /**
     * purchaseProduct
     * <p>
     * purchases a product from a store. updates the marketplace file with the quantity of the product after it has been
     * purchased by a user. updates the store purchaseInfo file with the receipt of the purchase. updates the
     * application/user userPurchases file with the receipt of the purchases as well. returns an error if the product is
     * not found or out of stock
     *
     * @param productName  the name of the product
     * @param storeName    the store the product is in
     * @param buyer        the user buying the product
     * @param purchaseFile the userPurchases file
     */
    public void purchaseProduct(String productName, String storeName, User buyer, File purchaseFile) {

        ArrayList<Store> list = new ArrayList<Store>();
        ArrayList<Product> pr;

        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            // constructs an array with the data in the store file
            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                pr = new ArrayList<Product>();
                if (productList.equals("[]")) {
                    // need this to show pr has empty store
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);
                    // throws an exception if the product list is empty -- probably should throw
                    // something more specific
                    //  throw new Exception();

                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]),
                                Double.parseDouble(divss[4]));

                        pr.add(prs);
                    }
                    Store store = new Store(div[0], div[1], pr);
                    list.add(store);
                }

                line = bfr.readLine();
            }
            bfr.close();

            boolean purchased = false;
            boolean found = false;


            Product product = null;
            for (int j = 0; j < list.size(); j++) {
                for (int k = 0; k < list.get(j).getProducts().size(); k++) {
                    if (list.get(j).getProducts().get(k).getName().equals(productName)
                            && list.get(j).getProducts().get(k).getStoreName().equals(storeName)) {
                        found = true;

                        int currentQuantity = list.get(j).getProducts().get(k).getQuantity();

                        if (currentQuantity >= 1) {
                            purchased = true;
                            product = list.get(j).getProducts().get(k);
                            list.get(j).getProducts().set(k,
                                    new Product(list.get(j).getProducts().get(k).getName(),
                                            list.get(j).getProducts().get(k).getStoreName(),
                                            list.get(j).getProducts().get(k).getDescription(), currentQuantity - 1,
                                            list.get(j).getProducts().get(k).getPrice()));

                        }
                    }
                }
            }

            if (found) {
                if (purchased) {

                    String productRecipt = "[" + buyer.toString() + "]" + "/" + "[" + product.getName() + "-" + product.getStoreName() + "-" + 1 + product.getPrice() + "]";

                    // write to store purchases file
                    try {
                        fos = new FileOutputStream(salesFile, true);
                        pw = new PrintWriter(fos);

                        pw.println(productRecipt);
                        pw.close();
                    } catch (Exception e) {
                        System.out.println("There was an error writing to the store sales file.");
                    }


                    // write to customer purchases file (?)
                    try {
                        fos = new FileOutputStream(purchaseFile, true);
                        pw = new PrintWriter(fos);

                        pw.println(productRecipt);
                        pw.close();
                    } catch (Exception e) {
                        System.out.println("There was an error writing to the customer purchases file.");
                    }

                    System.out.println();
                    System.out.println("Successfully Purchased the " + productName + "!");

                } else {

                    System.out.println("Not enough in stock!");
                }
            } else {

                System.out.println("Unable to find the item!");
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }

            pw.close();
            System.out.println();

        } catch (FileNotFoundException ex) {
            System.out.println("No Products!");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error reading the marketplace file!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The product data may be incomplete or corrupted!");
        } catch (Exception e) {
            System.out.println("There was an error purchasing the product!");
        }

    }

    /**
     * viewStore
     * <p>
     * prints out the data for a particular store, including the name and seller, and formatted information for each of
     * the products in the store
     *
     * @param storeName the name of the store
     */
    public void viewStore(String storeName) {
        ArrayList<String> listings = new ArrayList<>();

        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();
            int flag = 0;

            while (line != null) {
                // initial division into name/seller/[product list]
                String[] listing = line.split("/");
                String name = listing[0];
                String seller = listing[1];
                // extract and trim product list
                String productsString = listing[2];
                productsString = productsString.substring(1, productsString.length() - 1);


                if (name.equals(storeName)) {

                    // if store found, print name and seller
                    String initOutput = String.format("Store: %s, Seller: %s\n - Products - \n", name, seller);
                    System.out.println(initOutput);

                    // then print data for all products below it
                    // Product Info:\nName: %s, Desc: %s, Quantity: %s, Price: $%s"
                    // splits into data strings for individual products
                    String[] productsData = productsString.split(", ");

                    // for each product data string, print out information
                    for (String productDataString : productsData) {
                        String[] productData = productDataString.split("-");


                        String output = String.format("Product Name: %s  Description: %s  Quantity: %s  Price: %s", productData[0], productData[2], productData[3], productData[4]);
                        System.out.println();
                        System.out.println(output);
                    }

                    flag = 1;
                }


                line = bfr.readLine();
            }

            bfr.close();

            if (flag == 0) {
                System.out.println();
                System.out.println("Store does not exist!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No stores exist!");
        } catch (IOException e) {
            System.out.println("Error reading the sales file!");
        }
    }

    /**
     * viewSales
     * <p>
     * prints out a formatted list of all the sales made by a particular store. reads through the purchaseInfo file.
     * calculates the total revenue of the store by summing the sale prices of all sold items and prints it.
     *
     * @param storeName the name of the store
     */
    public void viewSales(String storeName) {
        // read in sales list file
        // parse to array same as other functions
        // split is different

        // print to console sales that match the current store

        try {
            fr = new FileReader(salesFile);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            int flag = 0;
            double totalRevenue = 0;
            while (line != null) {

                String[] cusSale = line.split("/");
                String customer = cusSale[0];
                String sale = cusSale[1];

                customer = customer.substring(1, customer.length() - 1);
                sale = sale.substring(1, sale.length() - 1);

                String[] customerData = customer.split("-");
                String[] saleData = sale.split("-");

                if (saleData[1].equals(storeName)) {

                    totalRevenue += Double.parseDouble(saleData[2]);
                    String output = String.format(
                            "Buyer: %s  Email: %s  Item: %s  Store: %s  Price: %.2f", customerData[2],
                            customerData[0], saleData[0], saleData[1], Double.parseDouble(saleData[2]));
                    flag = 1;
                    System.out.println();
                    System.out.print(output);
                }


                line = bfr.readLine();
            }

            bfr.close();

            if (flag == 0) {
                System.out.println();
                System.out.println("The store doesn't exist or has no associated purchases!");
            }

            System.out.printf("\nTotal Sales Revenue: %.2f", totalRevenue);

        } catch (FileNotFoundException e) {
            System.out.println("There are no purchases on record!");
        } catch (IOException e) {
            System.out.println("Error reading the sales file!");
        }
    }

    /**
     * showMarketplace
     * <p>
     * reads from the store info file and writes all product information into the marketplace file. prints products to
     * console.
     */
    public void showMarketplace() {
        // read from the stores file, and write the products in the marketplace file
        // print the product

        ArrayList<Product> market = new ArrayList<Product>();

        try {
            //  if (!fTwo.exists() || fTwo.length() == 0) {


            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {
                String[] div = line.split("/");
                String productList = div[2];
                if (productList.equals("[]")) {


                } else {
                    productList = productList.substring(1, productList.length() - 1);
                    String[] divs = productList.split(", ");
                    for (int i = 0; i < divs.length; i++) {
                        String[] divss = divs[i].split("-");
                        Product prs = new Product(divss[0], divss[1], divss[2], Integer.parseInt(divss[3]), Double.parseDouble(divss[4]));

                        market.add(prs);
                    }

                }

                line = bfr.readLine();
            }
            bfr.close();


            if (market.size() == 0) {
                System.out.println();
                System.out.println("No Products");
                System.out.println();
                return;
            }


            fos = new FileOutputStream(fTwo, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < market.size(); i++) {
                pw.println(market.get(i));
            }

            pw.close();


            for (int i = 0; i < market.size(); i++) {
                System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", market.get(i).getName(), market.get(i).getStoreName(), market.get(i).getPrice());
            }


        } catch (FileNotFoundException ex) {
            System.out.println();
            System.out.println("No Products");
        } catch (Exception e) {
            System.out.println("Error");
        }


    }

    /**
     * selectMarketplace
     * <p>
     * searches the marketplace for a product with a given productname. prints out the description and quantity
     * of the product.
     *
     * @param productName the name of the product to search for
     */
    public void selectMarketplace(String productName) {
        System.out.println();
        ArrayList<Product> market = new ArrayList<Product>();
        try {
            fr = new FileReader(fTwo);
            bfr = new BufferedReader(fr);

            String lines = bfr.readLine();

            while (lines != null) {
                String[] productFields = lines.split("-");
                market.add(new Product(productFields[0], productFields[1], productFields[2], Integer.parseInt(productFields[3]), Double.parseDouble(productFields[4])));
                lines = bfr.readLine();
            }
            bfr.close();

        } catch (FileNotFoundException ex) {
            System.out.println("No Products!");
            System.out.println();
            return;
        } catch (Exception e) {
            System.out.println("Error!");
            return;
        }

        if (market.size() == 0) {
            System.out.println("Market is empty!");
            return;
        }

        int flag = 0;
        for (int i = 0; i < market.size(); i++) {
            if (market.get(i).getName().equals(productName)) {
                System.out.printf("Description: %s   Quantity Available: %d\n", market.get(i).getDescription(), market.get(i).getQuantity());
                System.out.println();
                flag = 1;
            }
        }

        if (flag == 0) {
            System.out.println();
            System.out.println("Product does not exist");
        }
    }


    /**
     * searchMarketplace
     * <p>
     * searches the marketplace for a product given a parameter specifying whether to search according to product name,
     * store name, or description. returns any products whose relevant field contains the string that is used to search
     *
     * @param productName the string searched for
     * @param num         used to determine which field to search by
     */
    public void searchMarketplace(String productName, String num) {
        System.out.println();
        ArrayList<Product> market = new ArrayList<Product>();
        try {
            fr = new FileReader(fTwo);
            bfr = new BufferedReader(fr);

            String lines = bfr.readLine();

            while (lines != null) {
                String[] productFields = lines.split("-");
                market.add(new Product(productFields[0], productFields[1], productFields[2], Integer.parseInt(productFields[3]), Double.parseDouble(productFields[4])));
                lines = bfr.readLine();
            }
            bfr.close();

        } catch (FileNotFoundException ex) {
            System.out.println("No Products!");
            System.out.println();
            return;
        } catch (Exception e) {
            System.out.println("Error!");
            return;
        }

        if (market.size() == 0) {
            System.out.println("Market is empty!");
            return;
        }

        if (num.equals("1")) {
            int flag = 0;
            for (int i = 0; i < market.size(); i++) {
                if (market.get(i).getName().contains(productName)) {
                    System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", market.get(i).getName(), market.get(i).getStoreName(), market.get(i).getPrice());
                    flag = 1;
                }
            }
            if (flag == 0) {
                System.out.println();
                System.out.println("No such product exists!");
            }
        } else if (num.equals("2")) {
            int flag = 0;
            for (int i = 0; i < market.size(); i++) {
                if (market.get(i).getStoreName().contains(productName)) {
                    System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", market.get(i).getName(), market.get(i).getStoreName(), market.get(i).getPrice());
                    flag = 1;
                }
            }
            if (flag == 0) {
                System.out.println();
                System.out.println("No such store exists!");
            }
        } else if (num.equals("3")) {
            int flag = 0;
            for (int i = 0; i < market.size(); i++) {
                if (market.get(i).getDescription().contains(productName)) {
                    System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", market.get(i).getName(), market.get(i).getStoreName(), market.get(i).getPrice());
                    flag = 1;
                }
            }
            if (flag == 0) {
                System.out.println();
                System.out.println("No such description exists!");
            }
        }
    }

    /**
     * sortMarketplace
     * <p>
     * given a parameter determining what field to sort by, prints out a sorted version of the marketplace.
     *
     * @param num determines the sorting field
     */
    public void sortMarketplace(String num) {
        System.out.println();
        ArrayList<Product> market = new ArrayList<Product>();
        ArrayList<Double> marketPrice = new ArrayList<Double>();
        ArrayList<Integer> marketQuantity = new ArrayList<Integer>();

        try {
            fr = new FileReader(fTwo);
            bfr = new BufferedReader(fr);

            String lines = bfr.readLine();

            while (lines != null) {
                String[] productFields = lines.split("-");
                market.add(new Product(productFields[0], productFields[1], productFields[2], Integer.parseInt(productFields[3]), Double.parseDouble(productFields[4])));
                marketPrice.add(Double.parseDouble(productFields[4]));
                marketQuantity.add(Integer.parseInt(productFields[3]));

                lines = bfr.readLine();
            }
            bfr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No Products!");
            System.out.println();
            return;
        } catch (Exception e) {
            System.out.println("Error!");
            return;
        }

        Collections.sort(marketPrice);
        Collections.sort(marketQuantity);

        if (market.size() == 0) {
            System.out.println("Market is empty!");
            return;
        }


        if (num.equals("1")) {
            ArrayList<Product> sortedProduct = new ArrayList<Product>();
            for (int k = 0; k < marketPrice.size(); k++) {
                for (int j = 0; j < market.size(); j++) {
                    if (marketPrice.get(k) == market.get(j).getPrice()) {
                        sortedProduct.add(market.get(j));
                        market.remove(market.get(j));
                        break;
                    }
                }
            }
            try {
                fos = new FileOutputStream(fTwo, false);
                pw = new PrintWriter(fos);
                for (int i = 0; i < sortedProduct.size(); i++) {
                    pw.println(sortedProduct.get(i));
                }

                pw.close();

            } catch (Exception e) {
                System.out.println("Error!");
                return;
            }


            for (int i = 0; i < sortedProduct.size(); i++) {
                System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", sortedProduct.get(i).getName(), sortedProduct.get(i).getStoreName(), sortedProduct.get(i).getPrice());
            }


        } else if (num.equals("2")) {
            ArrayList<Product> sortedProduct = new ArrayList<Product>();
            for (int k = 0; k < marketQuantity.size(); k++) {
                for (int j = 0; j < market.size(); j++) {
                    if (marketQuantity.get(k) == market.get(j).getQuantity()) {
                        sortedProduct.add(market.get(j));
                        market.remove(market.get(j));
                        break;
                    }

                }
            }
            try {
                fos = new FileOutputStream(fTwo, false);
                pw = new PrintWriter(fos);
                for (int i = 0; i < sortedProduct.size(); i++) {
                    pw.println(sortedProduct.get(i));
                }

                pw.close();

            } catch (Exception e) {
                System.out.println("Error!");
                return;
            }


            for (int i = 0; i < sortedProduct.size(); i++) {
                System.out.printf("%-12s  Store: %-12s  Price: %.2f\n", sortedProduct.get(i).getName(), sortedProduct.get(i).getStoreName(), sortedProduct.get(i).getPrice());
            }
        }


    }

    /**
     * readStores
     * <p>
     * returns an arraylist of store objects representing the stores within the storeInfo file
     *
     * @return
     */
    public ArrayList<Store> readStores() {
        ArrayList<Product> pr;
        ArrayList<Store> storeList = new ArrayList<Store>();
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            // constructs an array with the data in the store file
            while (line != null) {
                String[] storeSplit = line.split("/");
                String productInfo = storeSplit[2];
                pr = new ArrayList<Product>();
                if (productInfo.equals("[]")) {
                    // need this to show pr has empty store
                    Store store = new Store(storeSplit[0], storeSplit[1], pr);
                    storeList.add(store);
                    // throws an exception if the product list is empty -- probably should throw
                    // something more specific
                    //  throw new Exception();

                } else {
                    productInfo = productInfo.substring(1, productInfo.length() - 1);
                    String[] productList = productInfo.split(", ");
                    for (int i = 0; i < productList.length; i++) {
                        String[] productInfoSplit = productList[i].split("-");
                        Product product = new Product(productInfoSplit[0], productInfoSplit[1], productInfoSplit[2],
                                Integer.parseInt(productInfoSplit[3]), Double.parseDouble(productInfoSplit[4]));

                        pr.add(product);
                    }
                    Store store = new Store(storeSplit[0], storeSplit[1], pr);
                    storeList.add(store);
                }

                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("Error reading the marketplace file!");
        }
        return storeList;
    }


    /**
     * Returns a string representation of the store: name/seller/[list of products]
     *
     * @return string representation of the stoor
     */
    public String toString() {
        return String.format("%s/%s/%s", name, seller, products);
    }

}
