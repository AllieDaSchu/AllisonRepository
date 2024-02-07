import java.io.File;
import java.io.PrintWriter;
// tests the core features of the application. expand on later.

public class ApplicationTester {

    public static void main(String[] args) throws Exception {

        // creates and clears files to avoid duplicate input over multiple runs
        File F1 = new File ("UserInfo.txt");
        File F2 = new File ("PurchaseInfo.txt");
        File F3 = new File ("StoreInfo.txt");
        File F4 = new File ("UserPurchases.txt");

        PrintWriter pw0 = new PrintWriter(F1);
        PrintWriter pw1 = new PrintWriter(F2);
        PrintWriter pw2 = new PrintWriter(F3);
        PrintWriter pw3 = new PrintWriter(F4);

        pw0.close();
        pw1.close();
        pw2.close();
        pw3.close();

        Application marketplace = new Application();
        User S1 = new User("S1", "S1", "S1", "Seller");
        User C1 = new User("C1", "C1", "C1", "Customer");
        User C2 = new User ("C2", "C2", "C2", "Customer");

        marketplace.createAccount(S1.getIdentifier(), S1.getPassword(), S1.getName(), S1.getRole());
        marketplace.createAccount(C1.getIdentifier(), C1.getPassword(), C1.getName(), C1.getRole());
        marketplace.createAccount(C2.getIdentifier(), C2.getPassword(), C2.getName(), C2.getRole());
        // accounts created successfully

        // trying to create an account that already exists
        marketplace.createAccount("C1", "C1pass", "C1", "Customer"); // causes account already exists error

        // trying to create an account with invalid role
        marketplace.createAccount("C3", "C3pass", "C3", "Trader"); // causes invalid input error

        // shutting down marketplace and trying to sign in again
        marketplace = null;
        marketplace = new Application();

        marketplace.signIn("S1", "S1pass");
        marketplace.signIn("C1", "C1pass");
        // sign in successful

        marketplace.signIn("S12", "S1pass"); // account does not exist case
        marketplace.signIn("C1", "C1pasta"); // invalid password case

        System.out.println("store testing: \n");
        // a new store object
        Store honda = new Store("Honda", S1.getIdentifier());
        Store kia = new Store("Kia", S1.getIdentifier());

        // product objects to add to the stores
        Product civic = new Product("Civic", "Honda", "midsize sedan", 20, 25500.50);
        Product soul = new Product("Soul", "Kia", "compact car", 22, 19999.99);
        Product stinger = new Product("Stinger", "Kia", "sports sedan", 1, 68999.99);

        honda.addStore(); // adds store to file

        honda.addProduct(civic, C2); // should NOT work due to user having incorrect permissions
        // shouldn't happen in the first place in the application main method

        honda.addProduct(civic, S1); // should successfully add a product
        honda.viewStore(honda.getName()); // displays store

        kia.addProduct(soul, S1); // fails due to kia not being added to the store file yet

        kia.addStore();
        kia.addStore();

        kia.addProduct(soul, S1);
        kia.addProduct(stinger, S1);
        // successfully adds products

        // purchasing products as customer C1
        Store defaultStore = new Store();

        defaultStore.purchaseProduct("Civic", "Honda", C1, new File("UserPurchases.txt")); // purchasing a product -- uses the purchases file in Application
        // succesful purchase!

        defaultStore.purchaseProduct("Covoc", "Honda", C1, new File("UserPurchases.txt"));
        // unsuccessful purchase, product not found

        defaultStore.purchaseProduct("Stinger", "Kia", C1, new File("UserPurchases.txt")); // successful
        defaultStore.purchaseProduct("Stinger", "Kia", C1, new File("UserPurchases.txt")); // unsuccessful - product out of stock

        // view product sales (seller)
        defaultStore.viewSales("Kia");
        System.out.println("");
        defaultStore.viewSales("Honda");

        // view product purchases (user)
        marketplace.viewPurchases("C1");

        // view product purchases (empty)
        marketplace.viewPurchases("C2");
    }
}


