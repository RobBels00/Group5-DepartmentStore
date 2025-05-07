/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.storeinventorychecker;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Categories extends CategoryRequirement {
    private String name;
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<Double> itemPrices = new ArrayList<>();
    private ArrayList<Integer> itemStocks = new ArrayList<>();
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Item management methods
    public void addItem(String itemName, double price, int stock) {
        itemNames.add(itemName);
        itemPrices.add(price);
        itemStocks.add(stock);
    }
    
    public boolean hasItem(String itemName) {
        return itemNames.contains(itemName);
    }
    
    public ArrayList<String> getItemNames() {
        return new ArrayList<>(itemNames);
    }
    
    public double getItemPrice(String itemName) {
        int index = itemNames.indexOf(itemName);
        if (index != -1) {
            return itemPrices.get(index);
        }
        return 0.0;
    }
    
    public int getItemStock(String itemName) {
        int index = itemNames.indexOf(itemName);
        if (index != -1) {
            return itemStocks.get(index);
        }
        return 0;
    }
    
    public void editItemName(String oldName, String newName) {
        int index = itemNames.indexOf(oldName);
        if (index != -1) {
            itemNames.set(index, newName);
        }
    }
    
    public void editItemPrice(String itemName, double newPrice) {
        int index = itemNames.indexOf(itemName);
        if (index != -1) {
            itemPrices.set(index, newPrice);
        }
    }
    
    public void editItemStock(String itemName, int newStock) {
        int index = itemNames.indexOf(itemName);
        if (index != -1) {
            itemStocks.set(index, newStock);
        }
    }
    
    public void removeItem(String itemName) {
        int index = itemNames.indexOf(itemName);
        if (index != -1) {
            itemNames.remove(index);
            itemPrices.remove(index);
            itemStocks.remove(index);
        }
    }
    
    @Override
    public void showStock() {
        if (itemNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items in category: " + name);
            return;
        }

        String message = "Category: " + name + "\n\n";

        for (int i = 0; i < itemNames.size(); i++) {
            message += "Name: " + itemNames.get(i) +
                       " | Price: ₱" + String.format("%.2f", itemPrices.get(i)) +
                       " | Stock: " + itemStocks.get(i) + "\n";
        }

        JOptionPane.showMessageDialog(null, message, "Inventory - " + name, JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void showNoStock() {
        boolean hasNoStock = false;
        String message = "Out of Stock Items in Category: " + name + "\n\n";

        for (int i = 0; i < itemNames.size(); i++) {
            if (itemStocks.get(i) == 0) {
                hasNoStock = true;
                message += "Name: " + itemNames.get(i) +
                           " | Price: ₱" + String.format("%.2f", itemPrices.get(i)) +
                           " | Stock: 0\n";
            }
        }

        if (!hasNoStock) {
            JOptionPane.showMessageDialog(null, "No out-of-stock items in category: " + name);
        } else {
            JOptionPane.showMessageDialog(null, message, "Out of Stock - " + name, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}