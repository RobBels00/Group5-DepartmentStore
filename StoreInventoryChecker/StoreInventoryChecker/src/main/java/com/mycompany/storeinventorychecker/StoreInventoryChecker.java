package com.mycompany.storeinventorychecker;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class StoreInventoryChecker {
    
    public static String upperCase(String name){
        if (name.contains(" ")){
            int spaceInd = 0;
            spaceInd = name.indexOf(" ");
            return name.substring(0,1).toUpperCase().concat((name.substring(1, spaceInd)).toLowerCase()) + " " +upperCase(name.substring(spaceInd + 1));
        }
        return name.substring(0,1).toUpperCase().concat((name.substring(1, name.length())).toLowerCase());
    }
    
    private static String currentUsername = "";
    private static ArrayList<Categories> categoryList = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        boolean loggedIn = login();
        
        if (loggedIn) {
            inventoryMethods.loadInventory(categoryList);
            showMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Program terminated.");
        }
    }
    
    private static boolean login() throws IOException {
        while (true) {
            String username = JOptionPane.showInputDialog("Enter username (or cancel to exit):");
            if (username == null) {
                return false;
            }
            
            String password = JOptionPane.showInputDialog("Enter password (or cancel to exit):");
            if (password == null) {
                return false;
            }
            
            File accountsFile = new File("accounts.txt");
            Scanner scanner = new Scanner(accountsFile);
            
            boolean correctUsername = false;
            boolean correctPassword = false;
            
            if (username.equals("admin")) {
                correctUsername = true;
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Check for the password only, since the username is always "admin"
                if (line.equals("Password: " + password)) {
                    correctPassword = true;
                    break; // Exit the loop if the password matches
                }
            }
            
            scanner.close();
            
            if (correctUsername && correctPassword) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                return true;
            } else {
                int choice = JOptionPane.showConfirmDialog(null, 
                    "Invalid username or password. Try again?", 
                    "Login Error", 
                    JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                    return false;
                }
            }
        }
    }
    
    //Shows main menu
    private static void showMenu() throws IOException {
    boolean running = true;

    while (running) {
        String menu =
                "Store Inventory System\n\n" +
                "1. Add category\n" + 
                "2. Remove category\n" + 
                "3. Add item\n" +
                "4. Edit item\n" + 
                "5. Remove item\n" + 
                "6. Browse stock\n" +
                "7. Browse unavailable stock\n" +
                "8. Exit";

        String choice = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.PLAIN_MESSAGE);

        if (choice == null) {
            running = false; // user closed the dialog
        } else if (checker.isInteger(choice)) {
            int selection = Integer.parseInt(choice);
            
            switch (selection) {
                case 1: categoryMethods.addCategory(categoryList); break;
                case 2: categoryMethods.removeCategory(categoryList); break;
                case 3: itemMethods.addItem(categoryList); break;
                case 4: itemMethods.editItem(categoryList); break;
                case 5: itemMethods.removeItem(categoryList); break;
                case 6: stockMethods.browseStock(categoryList); break;
                case 7: stockMethods.browseUnavailableStock(categoryList); break;
                case 8: running = false; break;
                default: JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }
            
            } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    
        // Only shown after the loop ends (dialog closed or user selected exit)
        JOptionPane.showMessageDialog(null, "Thank you for using the Store Inventory System!");
    }
}