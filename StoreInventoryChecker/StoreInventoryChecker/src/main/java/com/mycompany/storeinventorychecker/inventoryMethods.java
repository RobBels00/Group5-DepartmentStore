package com.mycompany.storeinventorychecker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class inventoryMethods {
    public static void loadInventory(ArrayList<Categories> categoryList) throws IOException {
        categoryList.clear();
        File inventoryFile = new File("inventory.txt");
        Scanner scanner = new Scanner(inventoryFile);
        
        Categories currentCategory = null;
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            
            // Check for category
            if (line.contains("Category:")) {
                String categoryName = line.substring(line.indexOf("Category:") + 9).trim(); //+10 because it starts to get the category name and "Category:" is 9 indexes
                currentCategory = new Categories();
                currentCategory.setName(categoryName);
                categoryList.add(currentCategory);
            } else if (line.contains("Name:")) {
                
                // Parse item line
                String itemLine = line;
                String name = "";
                double price = 0.0;
                int stock = 0;
                
                // Extract name
                if (itemLine.contains("Name:")) {
                    int nameStart = itemLine.indexOf("Name:") + 5;
                    int nameEnd = itemLine.indexOf("|");
                    if (nameEnd == -1) nameEnd = itemLine.length();
                    name = itemLine.substring(nameStart, nameEnd).trim();
                }
                
                // Extract price
                if (itemLine.contains("Price:")) {
                    int priceStart = itemLine.indexOf("Price:") + 6;
                    int priceEnd = itemLine.indexOf("|", priceStart);
                    if (priceEnd == -1) priceEnd = itemLine.length();
                    String priceStr = itemLine.substring(priceStart, priceEnd).trim();
                    priceStr = priceStr.replace("₱", "").trim();
                    
                    if (checker.isDouble(priceStr)) {
                        price = Double.parseDouble(priceStr);
                    } else {
                        // Handle incorrect price format
                        JOptionPane.showMessageDialog(null, "Invalid price format!");
                    }
                }
                
                // Extract stock
                if (itemLine.contains("Stock:")) {
                    int stockStart = itemLine.indexOf("Stock:") + 6;
                    int stockEnd = itemLine.indexOf("|", stockStart);
                    if (stockEnd == -1) stockEnd = itemLine.length();
                    String stockStr = itemLine.substring(stockStart, stockEnd).trim();
                    // Check if the stockStr is a valid integer
                    if (checker.isInteger(stockStr)) {
                        stock = Integer.parseInt(stockStr);
                    } else {
                        // Handle incorrect stock format
                        JOptionPane.showMessageDialog(null, "Invalid stock format!");
                    }
                }
                
                if (currentCategory != null) {
                    currentCategory.addItem(name, price, stock);
                }
            } else if (line.contains("-")) {
                // End of category
                currentCategory = null;
            }
        }
        scanner.close();
    }
    
    public static void updateInventoryFile(ArrayList<Categories> categoryList) throws IOException {
        FileWriter writer = new FileWriter("inventory.txt");
        
        for (int i = 0; i < categoryList.size(); i++) {
            Categories category = categoryList.get(i);
            
            // Write category
            writer.write("Category: " + category.getName() + "\n");
            
            // Write items
            ArrayList<String> itemNames = category.getItemNames();
            for (String itemName : itemNames) {
                double price = category.getItemPrice(itemName);
                int stock = category.getItemStock(itemName);
                
                writer.write("Name: " + itemName + " | Price: ₱" + price + " | Stock: " + stock + "\n");
            }
            
            // Write category separator
            writer.write("-\n\n");
        }
        
        writer.close();
    }
}
