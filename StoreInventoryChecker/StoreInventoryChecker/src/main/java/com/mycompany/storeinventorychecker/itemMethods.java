package com.mycompany.storeinventorychecker;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class itemMethods {
    public static void addItem(ArrayList<Categories> categoryList) throws IOException {
        if (categoryList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No categories available! Add a category first.");
            return;
        }

        String[] categoryNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }

        while (true) {
            String selection = JOptionPane.showInputDialog(null,
                    "Select category to add item to:\n" + String.join("\n", categoryNames),
                    "Add Item",
                    JOptionPane.PLAIN_MESSAGE);

            if (selection == null) return;

            if (!checker.isInteger(selection)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                continue;
            }

            int index = Integer.parseInt(selection) - 1;

            if (index < 0 || index >= categoryList.size()) {
                JOptionPane.showMessageDialog(null, "Invalid selection! No such category number.");
                continue;
            }

            // Valid category selected
            Categories selectedCategory = categoryList.get(index);

            while (true) {
                
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

                if (selectedCategory.hasItem(itemName)) {
                    int retry = JOptionPane.showConfirmDialog(null,
                            "Item already exists. Do you want to add another item?",
                            "Duplicate Item",
                            JOptionPane.YES_NO_OPTION);

                    if (retry == JOptionPane.YES_OPTION) {
                        continue; // Ask for another item name
                    } else {
                        break; // Go back to category selection
                    }
                }

                double price;
                while (true) {
                    String priceStr = JOptionPane.showInputDialog("Enter the price:");
                    if (priceStr == null) return;

                    if (!checker.isDouble(priceStr)) {
                        JOptionPane.showMessageDialog(null, "Invalid price format! Please enter a number.");
                        continue;
                    }

                    price = Double.parseDouble(priceStr);
                    if (price < 0) {
                        JOptionPane.showMessageDialog(null, "Price cannot be negative! Try again.");
                        continue;
                    }

                    break;
                }

                int stock;
                while (true) {
                    String stockStr = JOptionPane.showInputDialog("Enter item stock:");
                    if (stockStr == null) return;

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

                selectedCategory.addItem(itemName, price, stock);
                inventoryMethods.updateInventoryFile(categoryList);
                JOptionPane.showMessageDialog(null, "Item added successfully!");

                int again = JOptionPane.showConfirmDialog(null,
                        "Add another item to this category?", "Continue?", JOptionPane.YES_NO_OPTION);

                if (again != JOptionPane.YES_OPTION) {
                    break; // Go back to category selection
                }
            }
        }
    }
    
    public static void editItem(ArrayList<Categories> categoryList) throws IOException {
        if (categoryList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No categories available!");
            return;
        }

        String[] categoryNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }

        while (true) {
            String selection = JOptionPane.showInputDialog(null,
                    "Select category:\n" + String.join("\n", categoryNames),
                    "Edit Item",
                    JOptionPane.PLAIN_MESSAGE);

            if (selection == null) return;

            if (!checker.isInteger(selection)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                continue;
            }

            int categoryIndex = Integer.parseInt(selection) - 1;
            if (categoryIndex < 0 || categoryIndex >= categoryList.size()) {
                JOptionPane.showMessageDialog(null, "Invalid selection! No such category number.");
                continue;
            }

            Categories selectedCategory = categoryList.get(categoryIndex);

            while (true) {
                ArrayList<String> itemNames = selectedCategory.getItemNames();

                if (itemNames.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No items in this category!");
                    return;
                }

                String[] items = new String[itemNames.size()];
                for (int i = 0; i < itemNames.size(); i++) {
                    items[i] = (i + 1) + ". " + itemNames.get(i);
                }

                String itemSelection = JOptionPane.showInputDialog(null,
                        "Select item to edit:\n" + String.join("\n", items),
                        "Edit Item",
                        JOptionPane.PLAIN_MESSAGE);

                if (itemSelection == null) return;

                if (!checker.isInteger(itemSelection)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid item number!");
                    continue;
                }

                int itemIndex = Integer.parseInt(itemSelection) - 1;
                if (itemIndex < 0 || itemIndex >= itemNames.size()) {
                    JOptionPane.showMessageDialog(null, "Invalid item selection!");
                    continue;
                }

                String currentName = itemNames.get(itemIndex);
                String[] options = {"Name", "Price", "Stock"};

                String attribute = (String) JOptionPane.showInputDialog(null,
                        "What would you like to edit?",
                        "Edit Item",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (attribute == null) return;

                switch (attribute) {
                    case "Name":
                        
                        String newName;
                        while (true) {
                            newName = JOptionPane.showInputDialog("Enter new name:", currentName);
                            if (newName == null) {
                                // User pressed Cancel
                                return;
                            } else if (newName.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Item name cannot be empty!");
                                continue;
                            }
                            break;
                        }

                        newName = StoreInventoryChecker.upperCase(newName);
                        if (!newName.equals(currentName) && selectedCategory.hasItem(newName)) {
                            JOptionPane.showMessageDialog(null, "Item with that name already exists!");
                            return;
                        }
                        selectedCategory.editItemName(currentName, newName);
                        break;

                    case "Price":
                        double currentPrice = selectedCategory.getItemPrice(currentName);
                        while (true) {
                            String newPriceStr = JOptionPane.showInputDialog("Enter new price:", currentPrice);
                            if (newPriceStr == null) return;

                            if (!checker.isDouble(newPriceStr)) {
                                JOptionPane.showMessageDialog(null, "Invalid price format! Please enter a number.");
                                continue;
                            }

                            double newPrice = Double.parseDouble(newPriceStr);
                            if (newPrice < 0) {
                                JOptionPane.showMessageDialog(null, "Price cannot be negative!");
                                continue;
                            }

                            selectedCategory.editItemPrice(currentName, newPrice);
                            break;
                        }
                        break;

                    case "Stock":
                        int currentStock = selectedCategory.getItemStock(currentName);
                        while (true) {
                            String newStockStr = JOptionPane.showInputDialog("Enter new stock:", currentStock);
                            if (newStockStr == null) return;

                            if (!checker.isInteger(newStockStr)) {
                                JOptionPane.showMessageDialog(null, "Invalid stock format!");
                                continue;
                            }

                            int newStock = Integer.parseInt(newStockStr);
                            if (newStock < 0) {
                                JOptionPane.showMessageDialog(null, "Stock cannot be negative!");
                                continue;
                            }

                            selectedCategory.editItemStock(currentName, newStock);
                            break;
                        }
                        break;
                }

                inventoryMethods.updateInventoryFile(categoryList);
                JOptionPane.showMessageDialog(null, "Item updated successfully!");

                int again = JOptionPane.showConfirmDialog(null, "Edit another item in this category?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (again != JOptionPane.YES_OPTION) return;
            }
        }
    }

    
    public static void removeItem(ArrayList<Categories> categoryList) throws IOException {
        if (categoryList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No categories available!");
            return;
        }

        String[] categoryNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = (i + 1) + ". " + categoryList.get(i).getName();
        }

        while (true) {
            String selection = JOptionPane.showInputDialog(null,
                    "Select category to remove item from:\n" + String.join("\n", categoryNames),
                    "Remove Item",
                    JOptionPane.PLAIN_MESSAGE);

            if (selection == null) return;

            if (!checker.isInteger(selection)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                continue;
            }

            int index = Integer.parseInt(selection) - 1;

            if (index < 0 || index >= categoryList.size()) {
                JOptionPane.showMessageDialog(null, "Invalid selection! No such category number.");
                continue;
            }

            Categories selectedCategory = categoryList.get(index);

            while (true) {
                ArrayList<String> itemNames = selectedCategory.getItemNames();
                if (itemNames.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No items in this category!");
                    return;
                }

                String[] items = new String[itemNames.size()];
                for (int i = 0; i < itemNames.size(); i++) {
                    items[i] = (i + 1) + ". " + itemNames.get(i);
                }

                String itemSelection = JOptionPane.showInputDialog(null,
                        "Select item to remove:\n" + String.join("\n", items),
                        "Remove Item",
                        JOptionPane.PLAIN_MESSAGE);

                if (itemSelection == null) return;

                if (!checker.isInteger(itemSelection)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid item number!");
                    continue;
                }

                int itemIndex = Integer.parseInt(itemSelection) - 1;

                if (itemIndex < 0 || itemIndex >= itemNames.size()) {
                    JOptionPane.showMessageDialog(null, "Invalid item selection!");
                    continue;
                }
                String itemName = itemNames.get(itemIndex);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to remove item \"" + itemName + "\"?", "Confirm?", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION){
                    selectedCategory.removeItem(itemName);
                    inventoryMethods.updateInventoryFile(categoryList);
                    JOptionPane.showMessageDialog(null, "Item removed successfully!");
                }
                
                // Ask user if they want to continue
                int again = JOptionPane.showConfirmDialog(null, "Remove another item from this category?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (again != JOptionPane.YES_OPTION) return;
            }
        }
    }
}
