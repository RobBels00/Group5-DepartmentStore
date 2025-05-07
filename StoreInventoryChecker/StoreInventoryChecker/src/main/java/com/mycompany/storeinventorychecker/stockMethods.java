package com.mycompany.storeinventorychecker;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class stockMethods {
    public static void browseStock(ArrayList<Categories> categoryList) {
        // Show categories
        String[] categoryNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }

        if (categoryNames.length == 0) {
            JOptionPane.showMessageDialog(null, "No categories available!");
            return;
        }
        
        while(true){
            String categorySelection = JOptionPane.showInputDialog(null, 
                "Select category to browse:\n" + String.join("\n", categoryNames), 
                "Browse Stock", 
                JOptionPane.PLAIN_MESSAGE);

            if (categorySelection == null) return;

            // Check if the categorySelection is a valid integer
            if (checker.isInteger(categorySelection)) {
                int categoryIndex = Integer.parseInt(categorySelection) - 1;

                if (categoryIndex >= 0 && categoryIndex < categoryList.size()) {
                    Categories selectedCategory = categoryList.get(categoryIndex);

                    // Show stock
                    selectedCategory.showStock();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid category selection!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
            }
        }    
    }

    
    public static void browseUnavailableStock(ArrayList<Categories> categoryList) {
        // Show categories
        String[] categoryNames = new String[categoryList.size() + 1];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }
        
        int viewAll = categoryList.size();
        categoryNames[categoryList.size()] = (viewAll + 1) + ". View all unavailable stock";
        
        if (categoryNames.length == 0) {
            JOptionPane.showMessageDialog(null, "No categories available!");
            return;
        }

        while(true){
            // Ask for category selection
            String categorySelection = JOptionPane.showInputDialog(null, 
                "Select category to browse unavailable stock:\n" + String.join("\n", categoryNames), 
                "Browse Unavailable Stock", 
                JOptionPane.PLAIN_MESSAGE);

            // If the user cancels the input, exit the method
            if (categorySelection == null){
                return;
            }

            // Check if the input is a valid integer
            if (checker.isInteger(categorySelection)) {
                int categoryIndex = Integer.parseInt(categorySelection) - 1;

                // Check if the category index is valid
                if (categoryIndex >= 0 && categoryIndex < categoryList.size() + 1) {
                    
                    if(categoryIndex == categoryList.size()){
                        showAllUnavailableStock(categoryList);
                    } else{
                    Categories selectedCategory = categoryList.get(categoryIndex);
                    // Show unavailable stock
                    selectedCategory.showNoStock();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid category selection!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
            }
        }    
    }
    
    public static void showAllUnavailableStock(ArrayList<Categories> categoryList) {
        String message = "All Unavailable Stock:\n\n";
        boolean hasUnavailable = false;

        for (Categories category : categoryList) {
            String categoryMessage = "";
            for (String itemName : category.getItemNames()) {
                if (category.getItemStock(itemName) == 0) {
                    hasUnavailable = true;
                    categoryMessage += "Name: " + itemName + " | Price: ₱" + category.getItemPrice(itemName) + " | Stock: 0\n";
                }
            }
            if (!categoryMessage.equals("")) {
                message += "Category: " + category.getName() + "\n" + categoryMessage + "\n";
            }
        }

        if (hasUnavailable) {
            JOptionPane.showMessageDialog(null, message, "All Unavailable Stock", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No unavailable stock in any category.", "All Unavailable Stock", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}    
