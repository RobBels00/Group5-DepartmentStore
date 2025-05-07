package com.mycompany.storeinventorychecker;

import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class categoryMethods {
    
    public static void addCategory(ArrayList<Categories> categoryList) throws IOException {
        
        //Gets category name
        String categoryName;
        while (true) {
            categoryName = JOptionPane.showInputDialog("Enter category name:");
            if (categoryName == null) {
                // User pressed Cancel
                return;
            } else if (categoryName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Category name cannot be empty!");
                continue;
            }
            break;
        }
        categoryName = StoreInventoryChecker.upperCase(categoryName);
  
        // Check if category already exists
        for (Categories category : categoryList) {
            if (category.getName().equalsIgnoreCase(categoryName)) {
                JOptionPane.showMessageDialog(null, "Category already exists!");
                return;
            }
        }
        
        Categories newCategory = new Categories(); //Instantiating new category object
        newCategory.setName(categoryName);
        categoryList.add(newCategory);
        
        boolean addingItems = true;
        while (addingItems) {
            int choice = JOptionPane.showConfirmDialog(null, "Add an item to this category?", "Add Item", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                addingItems = false;
                continue;
            }
            
            String itemName;
            while (true) {
                itemName = JOptionPane.showInputDialog("Enter item name:");
                if (itemName == null) {
                    // User pressed Cancel
                    return;
                } else if (itemName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Item name cannot be empty!");
                    continue;
                }
                break;
            }
            itemName = StoreInventoryChecker.upperCase(itemName);
            
            //checking if price is valid format
            double price;
            while (true) { //in while loop to make sure price is in proper format
                String priceStr = JOptionPane.showInputDialog("Enter the price:");

                if (priceStr == null) {
                    // User pressed Cancel
                    return;
                }

                if (!checker.isDouble(priceStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid price format! Please enter a number.");
                    continue; // Go back to the top of the loop
                }

                price = Double.parseDouble(priceStr);

                if (price < 0) {
                    JOptionPane.showMessageDialog(null, "Price cannot be negative! Try again.");
                    continue;
                }

                break; // Valid input, break out of the loop
            }

            int stock;
            //checking if stock is valid format
            while(true){ //In while loop to make sure stock is in proper format
                String stockStr = JOptionPane.showInputDialog("Enter item stock:");

                if (stockStr == null){
                    return; //user pressed cancel
                }

                if (!checker.isInteger(stockStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid stock format!");
                    continue;
                }

                stock = Integer.parseInt(stockStr);

                if (stock < 0) {
                    JOptionPane.showMessageDialog(null, "Stock cannot be negative!");
                    continue;
                }

                break;
            }

            newCategory.addItem(itemName, price, stock);
            // Update the inventory file
            inventoryMethods.updateInventoryFile(categoryList);
            JOptionPane.showMessageDialog(null, "Item added successfully!");
        }
        JOptionPane.showMessageDialog(null, "Category added successfully!");
    }
    
    public static void removeCategory(ArrayList<Categories> categoryList) throws IOException {
        
        while(true){
        String[] categoryNames = new String[categoryList.size()];
        
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }
        
        if (categoryNames.length == 0) {
            JOptionPane.showMessageDialog(null, "No categories to remove!");
            return;
        }
        
            String selection = JOptionPane.showInputDialog(null, 
                "Select category to remove:\n" + String.join("\n", categoryNames), 
                "Remove Category", 
                JOptionPane.PLAIN_MESSAGE);

            if (selection == null) return;

            if (checker.isInteger(selection)) {
                int index = Integer.parseInt(selection) - 1; //Minus 1 because of index count starting at 0
                if (index >= 0 && index < categoryList.size()) {
                    String categoryName = categoryList.get(index).getName();
                    categoryList.remove(index);
                    inventoryMethods.updateInventoryFile(categoryList);
                    JOptionPane.showMessageDialog(null, "Category '" + categoryName + "' removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid selection!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
            }
            
        }
    }
}
