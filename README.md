CS 180 Project 4 - USAGE INFORMATION

INSTRUCTIONS FOR RUNNING AND COMPILING THE APPLICATION:
---------------------------------------------------------------------------------------------------
1) run the Application.java class
2) follow instructions in console to create accounts, sign in, or use other program functionality
3) program will read, parse, and create .txt files as needed to store data after the program is closed

note: ALL TEXT FIELDS FOR PRODUCTS, STORES, OR USERS MUST NOT CONTAIN THE CHARACTERS “ ,” , “/”, or “-”. USERS MUST MATCH CASES EXACTLY WHEN SEARCHING/ACCESSING PRODUCTS.

SUBMISSIONS
---------------------------------------------------------------------------------------------------
- Avi Puranik submitted report to brightspace
- Hosung Ryu submitted Vocareum workspace

CLASS DESCRIPTIONS
---------------------------------------------------------------------------------------------------

## Application.java
- contains the main method that actually runs the program
- calls all other classes
- supports the user interface and all menus

methods:
- signIn
  - takes in arguments representing the user input username and password and tries to sign in the user
  - if the login information matches information present in the UserInfo file
- createAccount
  - Creates an account with a defined email, password, name, and role.
- editAccount
  - updates an account's name and password with new values
- deleteAccount
  - deletes an account by removing it from the login information file. purchases made by the account will remain in the relevant files
- viewPurchases
  - Prints out a list of the purchases made by a user
- customerOpenCart
  - Opens up the shopping cart and presents the users options as to what they can do with the shopping cart
- buyCart
  - Iterates through a shoppingCart's products and calls purchaseProduct to buy each item in the cart
  - Clears cart afterward
- customerAddToCart
  - Allows the customer to add to the shopping cart
- findProduct
  - Finds and returns a product in the stores and products list given its name and store
- getCurrentShoppingCart
  - Allows user to retrieve the current shopping cart
- printAllCarts
  - Prints all shopping carts for the seller
- main
  - runs entire program

## Store.java
- contains the functionality needed to create, access, and modify stores
- contains functionality to read, print, and edit user purchase data, sales, store data and sales, etc
- new stores are called to be created within Application and are accessed by methods within application that call
methods in Store that print to the console

methods (not including constructors or getters/setters):
- addStore
  - Creates and adds a store to the StoreInfo file if it doesn't already exist. Prints an error if the store exists.
- removeStore
  - Removes a store from the list/file if it exists. Prints out an error if it doesn't exist
- addProduct
  - adds a new product to a store that the seller owns. checks if the seller calling this method owns the store that it is attempting to modify
- removeProduct
  - removes a product from the store. checks to make sure that the user calling the function is the seller that owns the store
- editProduct
  - edits a specific product in a specific store. checks to make sure that the user calling the function is the seller that owns store
- purchaseProduct
  - purchases a product from a store. updates the marketplace file with the quantity of the product after it has been purchased by a user. 
  - updates the store purchaseInfo file with the receipt of the purchase. updates the application/user userPurchases file with the receipt of the purchases as well. 
  - returns an error if the product is not found or out of stock
- viewStore
  - returns a formatted version of the store's info
- viewSales
  - returns a formatted list of all purchases made from the store
  - includes customer info, product bought info, and total revenue
- showMarketplace
  - prints the marketplace to the console and writes any product changes to the marketplace text file
- selectMarketplace
  - searches the marketplace for a product and prints out the product details
- searchMarketplace
  - allows the user to search the marketplace with a string field
- sortMarketplace
  - allows the user to return a sorted list representing the products in the marketplace
- toString
  - returns a store's info as a string
  

## User.java
- contains the functionality needed to create and modify users
- allows other classes to access user name, identifiers, passwords, permissions, and shopping carts
- accessed by Application (where new uers are created as Sellers or Customers), Store, and ShoppingCart

## Product.java
- contains the functionality needed to create and modify a product that goes into a store
- allows other classes to access product data
- accesssed by Store, Application, and ShoppingCart
- new products are created and added into stores, where they are then parsed into the store text file
- when a product needs to be edited in a store, the text file is used to create a modifyable object that is written back to the file

## ShoppingCart.java
- contains the functionality needed to run a shopping cart for a specific user
- shopping carts are persistent lists of products that allow a user to save products to buy later and buy them all at once

methods:
- shoppingCart
  - constructor that reads in dta from a user shopping cart file
- writeCartFile 
  - writes to shopping cart file
- productAsFileString
  - turns a Product object into string output
- productFromFileString
  - creates a Product object from string input
- printCart
  - prints the shopping cart to terminal, if not empty
- addProduct
  - adds a product to the cart and corresponding file
- removeProduct
  - removes a product from the cart and file
- deleteCartFile
  - deletes a cart from the shopping cart file
- clearCart
  - clears the cart file of all contents

TESTING
---------------------------------------------------------------------------------------------------
General testing for the full application is documented in the TestCaseInfo.txt text file.
Testing for the Application is also done using the ApplicationTester.java file. It will call and tset some of the base functions of the class.
