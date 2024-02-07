import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class that is used to run a marketplace application.
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version April 9, 2023
 */

public class Application {

    //private ObjectOutputStream writer;
    //private ObjectInputStream reader;
    private FileOutputStream fos;
    private PrintWriter pw;
    private File f = new File("UserInfo.txt"); // stores user login information
    private File purchaseFile = new File("UserPurchases.txt"); // stores receipts of user purchases
    private FileReader fr;
    private BufferedReader bfr;
    private ArrayList<User> list; // list of users
    private User loginUser; // representing the current user of the application
    //private File individualCart;

    /**
     * Sign in
     * <p>
     * A message that takes in arguments representing the user input username and password and tries to sign in the user
     * if the login information matches information present in the UserInfo file
     *
     * @param email    // the email the user enters
     * @param password // the password the user enters
     * @return returns the role of the user, or an empty string if the login attempt is unsuccessful
     */
    public String signIn(String email, String password) {
        System.out.println();

        try {
            // Read from the file, and store each line in the file as a User object and store it in the arraylist list
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            list = new ArrayList<User>();
            String line = bfr.readLine();
            while (line != null) {
                String[] div = line.split("-");
                User users = new User(div[0], div[1], div[2], div[3]);
                list.add(users);

                line = bfr.readLine();
            }
            bfr.close();
            //?
            int flag = 0;

            // go through the arraylist list and check if the account with the email and password exist
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdentifier().equals(email) && list.get(i).getPassword().equals(password)) {
                    System.out.println("Sign in successful");
                    User user = list.get(i);
                    loginUser = user;
                    flag = 1;

                    if (user.getRole().equals("Customer")) {
                        return "Customer";
                    } else if (user.getRole().equals("Seller")) {
                        return "Seller";
                    }
                } else if (list.get(i).getIdentifier().equals(email)) {
                    System.out.println("Invalid Password!");
                    return "";
                }
            }
            if (flag == 0) {
                System.out.println("Account does not exist!");

            }
        } catch (FileNotFoundException e) {
            System.out.println("Account does not exist!");
        } catch (Exception ex) {
            System.out.println("Error");
        }

        return "";
    }

    /**
     * createAccount
     * <p>
     * Creates an account with a defined email, password, name, and role.
     *
     * @param email    // the user entered email
     * @param password // the user entered password
     * @param name     // the user entered name
     * @param role     // the user entered role
     */
    public void createAccount(String email, String password, String name, String role) {
        System.out.println();
        User users = null;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            list = new ArrayList<User>();
            String line = bfr.readLine();
            while (line != null) {
                String[] div = line.split("-");
                User user = new User(div[0], div[1], div[2], div[3]);
                list.add(user);

                line = bfr.readLine();
            }

            bfr.close();

            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdentifier().equals(email)) {
                    System.out.println("Account already exists");
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                if (!role.equals("Customer") && !role.equals("Seller")) {
                    System.out.println();
                    System.out.println("Invalid Role!");
                    return;
                }
                users = new User(email, password, name, role);
                System.out.println();


                fos = new FileOutputStream(f, true);
                pw = new PrintWriter(fos);
                pw.println(users);

                pw.close();

                System.out.println("Account created successfully!");
            }
        } catch (FileNotFoundException e) {
            users = new User(email, password, name, role);
            System.out.println();

            try {

                fos = new FileOutputStream(f, true);
                pw = new PrintWriter(fos);
                pw.println(users);

                pw.close();

                System.out.println("Account created successfully!");
            } catch (Exception ex) {
                System.out.println("Error!");
            }

        } catch (Exception exc) {
            System.out.println("Error!");
        }


    }

    /**
     * editAccount
     * <p>
     * updates an account's name and password with new values
     *
     * @param newName     // the new name to assign to the account
     * @param newPassword // the new password to assign to the account
     */
    public void editAccount(String newName, String newPassword) {
        try {
            System.out.println();
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                String[] div = line.split("-");
                User users = new User(div[0], div[1], div[2], div[3]);
                if (users.toString().equals(loginUser.toString())) {
                    users.setName(newName);
                    users.setPassword(newPassword);
                    loginUser.setName(newName);
                    loginUser.setPassword(newPassword);
                }
                list.add(users.toString());
                line = bfr.readLine();
            }

            bfr.close();


            try {

                fos = new FileOutputStream(f, false);
                pw = new PrintWriter(fos);
                for (int j = 0; j < list.size(); j++) {
                    pw.println(list.get(j));
                }

                pw.close();
                System.out.println();
                System.out.println("Account edited successfully");

            } catch (Exception e) {
                System.out.println("Error!");

            }


        } catch (Exception e) {
            System.out.println("Error!");
        }


    }

    /**
     * deleteAccount
     * <p>
     * deletes an account by removing it from the login information file. purchases made by the account will remain
     * in their respective files
     */
    public void deleteAccount() {
        try {
            // can only once? not each time using methods?
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);

            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                String[] div = line.split("-");
                User users = new User(div[0], div[1], div[2], div[3]);
                list.add(users.toString());
                line = bfr.readLine();
            }

            bfr.close();

            list.remove(loginUser.toString());

            if (loginUser.getShoppingCart() != null) {
                loginUser.getShoppingCart().deleteCartFile();
            }

            fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int j = 0; j < list.size(); j++) {
                pw.println(list.get(j));

            }

            pw.close();
            Store store = new Store(loginUser.getIdentifier());
            store.removeStore();
            System.out.println("Account deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!");
        }
    }

    /**
     * Prints out a list of the purchases made by a user
     *
     * @param userName // the name of the user
     */
    public void viewPurchases(String userName) {

        try {
            fr = new FileReader(purchaseFile);
            bfr = new BufferedReader(fr);

            String line = bfr.readLine();

            while (line != null) {

                String[] cusSale = line.split("/");
                String customer = cusSale[0];
                String sale = cusSale[1];

                customer = customer.substring(1, customer.length() - 1);
                sale = sale.substring(1, sale.length() - 1);

                String[] customerData = customer.split("-");
                String[] saleData = sale.split("-");


                if (customerData[2].equalsIgnoreCase(userName)) {

                    String output = String.format(
                            "Your Email: %s Item: %s Store: %s Price: %s",
                            customerData[1], saleData[0], saleData[1], saleData[2]);

                    System.out.println(output);
                }

                line = bfr.readLine();
            }

            bfr.close();

        } catch (FileNotFoundException e) {
            System.out.println("You have no purchases on record!");
        } catch (IOException e) {
            System.out.println("Error reading the purchases file!");
        }

    }

    /**
     * buyCart
     * <p>
     * Iterates through a shoppingCart's products and calls purchaseProduct to buy each item in the cart
     * Clears cart afterward
     *
     * @param shoppingCart the shopping cart to buy from
     */
    public void buyCart(ShoppingCart shoppingCart) {
        ArrayList<Product> products = shoppingCart.getProducts();
        Store cartPurchase = new Store();
        for (int i = 0; i < products.size(); i++) {
            cartPurchase.purchaseProduct(products.get(i).getName(), products.get(i).getStoreName(), this.loginUser, this.purchaseFile);
        }
        shoppingCart.clearCart();
    }

    /**
     * customerAddToCart
     * <p>
     * Allows the customer to add to the shopping cart
     *
     * @param sc    a scanner object to read user input
     * @param store a store to find a product in
     */
    public void customerAddToCart(Scanner sc, Store store) {
        System.out.println();
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();
        System.out.print("Enter store name: ");
        String storeName = sc.nextLine();

        Product productToCart = this.findProduct(storeName, productName);
        if (productToCart == null) {
            System.out.println("Product or store does not exist");
        } else {
            getCurrentShoppingCart().addProduct(productToCart);
            System.out.println("Product has successfully been added to the cart!");
        }
    }

    /**
     * findProduct
     * <p>
     * Finds and returns a product in the stores and products list given its name and store
     *
     * @param storeName   the name of the store the product is in
     * @param productName the name of the product
     * @return the product as a Product object or null if not found
     */
    public Product findProduct(String storeName, String productName) {
        ArrayList<Store> stores = new Store().readStores();

        for (int i = 0; i < stores.size(); i++) {
            //Find product
            if (stores.get(i).getName().equals(storeName)) {
                ArrayList<Product> localProducts = stores.get(i).getProducts();
                for (int j = 0; j < localProducts.size(); j++) {
                    if (localProducts.get(j).getName().equals(productName)) {
                        return localProducts.get(j);
                    }
                }
            }
        }
        // return null if not found
        return null;
    }

    /**
     * getCurrentShoppingCart
     * <p>
     * Allows user to retrieve the current shopping cart
     *
     * @return the current shopping cart
     */
    public ShoppingCart getCurrentShoppingCart() {
        return this.loginUser.getShoppingCart();
    }

    /**
     * printAllCarts
     * <p>
     * Prints all shopping carts for the seller
     */
    public void printAllCarts() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("Customer")) {
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println(list.get(i).getName() + " - " + list.get(i).getIdentifier());
                list.get(i).getShoppingCart().printCart();
            }
        }
        System.out.print("---------------------------------------------------------------------------------------");
    }

    /**
     * MAIN METHOD FOR THE APPLICATION
     * runs all input/output methods
     *
     * @param args **
     */
    public static void main(String[] args) {
        Application application = new Application();
        Scanner sc = new Scanner(System.in);
        boolean valid; // used for input validation


        // sign in or create account
        System.out.println("Welcome!");
        String option;
        String signIn = "";
        String menuChoice = "";

        boolean runMarketPlace = true;

        do {
            System.out.println("");
            System.out.println("Sign in: 1  Create Account: 2  Quit Program: 3");
            option = sc.nextLine();
            if (option.equals("1")) {

                // LOOP THAT RUNS WHILE SIGN IN IS UNSUCCESSFUL
                // sign in
                System.out.println();
                System.out.println("Sign in");
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                signIn = application.signIn(email, password);

                boolean flag;

                flag = !signIn.equals("");

                while (flag) {

                    if (signIn.equals("Customer")) {
                        // if sign in user is customer
                        System.out.println();
                        System.out.println("Menu");
                        System.out.println();
                        System.out.println("Log out: 1  Delete Account: 2  Edit Account: 3");
                        System.out.println("Marketplace: 4  Shopping Cart: 5  View Purchases: 6");

                        System.out.println("-----------------------------------------------------------------------");
                        // INPUT VALIDATE
                        menuChoice = sc.nextLine();

                        if (menuChoice.equals("1")) {
                            System.out.println();
                            System.out.println("Log out successful");
                            flag = false;
                        } else if (menuChoice.equals("2")) {
                            application.deleteAccount();
                            flag = false;
                        } else if (menuChoice.equals("3")) {
                            // edit account
                            System.out.println();
                            System.out.print("Enter new name: ");
                            String newName = sc.nextLine();
                            System.out.print("Enter new password: ");
                            String newPassword = sc.nextLine();
                            application.editAccount(newName, newPassword);
                        } else if (menuChoice.equals("4")) {
                            boolean flagTwo = true;
                            while (flagTwo) {

                                // marketplace
                                System.out.println();
                                System.out.println("MARKETPLACE");

                                Store storeFour = new Store();


                                storeFour.showMarketplace();


                                System.out.println();// sort, search, buy
                                System.out.println("View Product: 1  Search Product: 2  Sort Marketplace: 3  Purchase Product: 4  Add to Cart: 5  View Purchases: 6  Menu: 7");

                                // INPUT VALIDATE
                                String searchSort = sc.nextLine();
                                if (searchSort.equals("1")) {
                                    // view product
                                    System.out.println();
                                    System.out.print("Enter product name: ");
                                    String productName = sc.nextLine();
                                    storeFour.selectMarketplace(productName);
                                } else if (searchSort.equals("2")) {
                                    // search product
                                    System.out.println();
                                    System.out.println("Search Product by Product Name: 1  Store Name: 2  Description: 3");

                                    // INPUT VALIDATE AFTER THIS
                                    String searchProduct = sc.nextLine();
                                    if (searchProduct.equals("1")) {
                                        System.out.println();
                                        System.out.print("Enter product name: ");
                                        String searchProductName = sc.nextLine();
                                        storeFour.searchMarketplace(searchProductName, "1");
                                    } else if (searchProduct.equals("2")) {
                                        System.out.println();
                                        System.out.print("Enter store name: ");
                                        String searchProductName = sc.nextLine();
                                        storeFour.searchMarketplace(searchProductName, "2");
                                    } else if (searchProduct.equals("3")) {
                                        System.out.println();
                                        System.out.print("Enter description: ");
                                        String searchProductName = sc.nextLine();
                                        storeFour.searchMarketplace(searchProductName, "3");
                                    } else {
                                        System.out.println("Invalid Input!");
                                    }

                                } else if (searchSort.equals("3")) {
                                    // sort marketplace
                                    System.out.println();
                                    System.out.println("Sort Marketplace by Price: 1  Quantity Available: 2");
                                    String priceOrQuantity = sc.nextLine();
                                    if (priceOrQuantity.equals("1")) {
                                        //sort by price
                                        storeFour.sortMarketplace("1");
                                    } else if (priceOrQuantity.equals("2")) {
                                        // sort by quantity
                                        storeFour.sortMarketplace("2");
                                    }

                                } else if (searchSort.equals("4")) {
                                    // purchase product!!!!!!!!!!!!
                                    System.out.println();
                                    System.out.print("Enter product name: ");
                                    String purchaseProduct = sc.nextLine();
                                    System.out.print("Enter store name: ");
                                    String purchaseStore = sc.nextLine();
                                    Store storeFive = new Store();
                                    storeFive.purchaseProduct(purchaseProduct, purchaseStore, application.loginUser, application.purchaseFile);


                                } else if (searchSort.equals("5")) {
                                    // add to shopping cart
                                    application.customerAddToCart(sc, storeFour);
                                } else if (searchSort.equals("6")) {
                                    application.viewPurchases(application.loginUser.getName());
                                } else if (searchSort.equals("7")) {
                                    flagTwo = false;
                                } else {
                                    System.out.println("Invalid Input!");
                                }


                            }

                        } else if (menuChoice.equals("5")) {
                            ShoppingCart shoppingCart = this.loginUser.getShoppingCart();

                            int removeAgain;
                            int removeQuestion;
                            do {
                                //When cart is empty
                                if (shoppingCart.printCart() == false) {
                                return;
                            }

                            //Cart has items
                            System.out.println("Buy Cart: 1  Remove Products: 2  Menu: 3");

                            try {
                                removeQuestion = sc.nextInt();
                            } catch (InputMismatchException e) {
                                removeQuestion = 4;
                            }
                            sc.nextLine();

                            //Send to Menu
                            if (removeQuestion == 3) {
                                return;
                            } else if (removeQuestion == 2) {
                                do {
                                // INPUT VALIDATE
                                System.out.println("Enter the number of the product you want to remove:");

                                int removeChoice = sc.nextInt();
                                sc.nextLine();

                                shoppingCart.removeProduct(removeChoice);
                                shoppingCart.printCart();
                                do {
                                    System.out.println("Would you like to remove another product?");
                                    System.out.println("1: Yes  2: No");
                                    removeAgain = sc.nextInt();
                                    sc.nextLine();
                                } while (removeAgain < 1 || removeAgain > 2);

                        } while (removeAgain == 1);
                    } else if (removeQuestion == 1) {
                        this.buyCart(shoppingCart);
                    } else {
                        System.out.print("Invalid Choice!");
                    }

                    // catch
                } while (removeQuestion == 1 || removeQuestion == 2);
                        } else if (menuChoice.equals("6")) {
                            application.viewPurchases(application.loginUser.getName());
                        } else {
                            // if the input wasn't any of the menu choices
                            System.out.println("Enter a valid choice!");
                        }


                    } else if (signIn.equals("Seller")) {
                        // if sign in user is seller
                        System.out.println();
                        System.out.println("Menu");
                        System.out.println();
                        System.out.println("Log out: 1  Delete Account: 2  Edit Account: 3");
                        System.out.println("Create Store: 4  Create Product: 5  Edit Product: 6  Delete Product: 7");
                        System.out.println("View Store: 8  View Store Sales: 9  View Customer Carts: 0");
                        System.out.println("---------------------------------------------------------------------------------------");
                        // list of stores
                        // method that displays the stores
                        // INPUT VALIDATE
                        menuChoice = sc.nextLine();

                        if (menuChoice.equals("1")) {
                            // log out
                            System.out.println();
                            System.out.println("Log out successful");
                            flag = false;
                        } else if (menuChoice.equals("2")) {
                            // delete account
                            application.deleteAccount();
                            flag = false;
                        } else if (menuChoice.equals("3")) {
                            // edit account
                            System.out.println();
                            System.out.print("Enter new name: ");
                            String newName = sc.nextLine();
                            System.out.print("Enter new password: ");
                            String newPassword = sc.nextLine();
                            application.editAccount(newName, newPassword);
                        } else if (menuChoice.equals("4")) {
                            // create store
                            System.out.println();
                            System.out.print("Enter store name: ");
                            String storeName = sc.nextLine();
                            Store store = new Store(storeName, application.loginUser.getIdentifier());
                            store.addStore();

                        } else if (menuChoice.equals("5")) {
                            // create product
                            // INPUT VALIDATE
                            Store storeTwo = new Store();

                            try {
                                System.out.println();
                                System.out.print("Enter the product name: ");
                                String productName = sc.nextLine();
                                System.out.print("Enter the store name: ");
                                String storeName = sc.nextLine();
                                System.out.print("Enter the product description: ");
                                String productDescription = sc.nextLine();
                                System.out.print("Enter the product quantity: ");
                                int productQuantity = Integer.parseInt(sc.nextLine());
                                System.out.print("Enter the product price: ");
                                double productPrice = Double.parseDouble(sc.nextLine());

                                Product product = new Product(productName, storeName, productDescription, productQuantity, productPrice);
                                storeTwo.addProduct(product, application.loginUser);

                            } catch (Exception e) {
                                System.out.println("Invalid Input!");
                            }

                        } else if (menuChoice.equals("6")) {
                            // edit product
                            // INPUT VALIDATE
                            Store storeFour = new Store();

                            try {
                                System.out.println();
                                System.out.print("Enter the product name: ");
                                String editProductName = sc.nextLine();
                                System.out.print("Enter the store name: ");
                                String editStoreName = sc.nextLine();
                                System.out.print("Enter the new product description: ");
                                String newProductDescription = sc.nextLine();
                                System.out.print("Enter the new product quantity: ");
                                int newProductQuantity = Integer.parseInt(sc.nextLine());
                                System.out.print("Enter the new product price: ");
                                double newProductPrice = Double.parseDouble(sc.nextLine());

                                storeFour.editProduct(editProductName, editStoreName, newProductDescription, newProductQuantity, newProductPrice, application.loginUser);

                            } catch (Exception e) {
                                System.out.println("Invalid Input!");
                            }

                        } else if (menuChoice.equals("7")) {
                            // delete product
                            System.out.println();
                            System.out.print("Enter the product name: ");
                            String deleteProductName = sc.nextLine();
                            Store storeThree = new Store();
                            storeThree.removeProduct(deleteProductName, application.loginUser);

                        } else if (menuChoice.equals("8")) {
                            System.out.print("Enter the Store Name: ");
                            String viewStoreName = sc.nextLine();
                            Store storeSeven = new Store();
                            storeSeven.viewStore(viewStoreName);

                        } else if (menuChoice.equals("9")) {
                            //view store!!!!!!!!!!!!!

                            System.out.println();
                            System.out.print("Enter the Store Name: ");
                            String viewStoreSalesName = sc.nextLine();
                            Store storeSix = new Store();
                            storeSix.viewSales(viewStoreSalesName);


                        } else if (menuChoice.equals("0")) {
                            application.printAllCarts();
                        } else {
                            // if the input is not a valid menu choice
                            System.out.println("Enter a valid choice!");
                        }
                    }
                }
            } else if (option.equals("2")) {
                // create Account
                System.out.println();
                System.out.println("Create Account");
                System.out.print("Email: ");
                String emailCreate = sc.nextLine();
                System.out.print("Password: ");
                String passwordCreate = sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Role (Seller or Customer): ");
                String role = sc.nextLine();
                application.createAccount(emailCreate, passwordCreate, name, role);

            } else if (option.equals("3")) {
                runMarketPlace = false;
                System.out.println("See you next time!");

            } else {
                System.out.println("Choose valid choice!");
            }

        } while (runMarketPlace && (option.equals("2") || option.equals("1") || signIn.equals("") || menuChoice.equals("1")));
    }
}
