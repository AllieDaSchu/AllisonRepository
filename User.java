import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class that works to create and change a user of a marketplace application.
 *
 * <p> Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version April 9, 2023
 */

public class User implements Serializable {


    //user fields

    private final String email; // the user's email -- THIS FIELD IS UNCHANGEABLE!
    private String name; // the user's username
    private String password; // the user's password
    private final String role; // the user's role -- THIS FIELD IS UNCHANGEABLE
    private ShoppingCart shoppingCart; // the user's shopping cart

    /**
     * User
     * <p>
     * constructor for the user class
     *
     * @param email    the user email
     * @param password the user password
     * @param name     the user's name
     * @param role     the user role
     */
    public User(String email, String password, String name, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /* Getters */

    /**
     * getIdentifier
     * <p>
     * returns the email identifier of the account
     *
     * @return user email
     */
    public String getIdentifier() {
        return this.email;
    }

    /**
     * getName
     * <p>
     * returns the user's name
     *
     * @return user name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getRole
     * <p>
     * returns the user role
     *
     * @return user role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * getPassword
     * <p>
     * returns the user password
     *
     * @return user password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * getShoppingCart
     * <p>
     * returns the user's ShoppingCart
     *
     * @return user ShoppingCart object
     */
    public ShoppingCart getShoppingCart() {
        if (!(shoppingCart == null)) {
            return shoppingCart;
        }

        if (this.getRole().equals("Customer")) {
            shoppingCart = new ShoppingCart(this);
            return shoppingCart;
        } else {
            return null;
        }
    }

    /* Setters */
    // There's no setEmail because those are final, you can't change them

    /**
     * setName
     * <p>
     * sets the user name to a new value
     *
     * @param name the user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setPassword
     * <p>
     * sets the user password to a new value
     *
     * @param password the user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * toString
     * <p>
     * returns a string representation of the user: email-password-name-role
     *
     * @return a string representing the user
     */
    @Override
    public String toString() {


        return String.format("%s-%s-%s-%s", this.email, this.password, this.name, this.role);
    }
} 
