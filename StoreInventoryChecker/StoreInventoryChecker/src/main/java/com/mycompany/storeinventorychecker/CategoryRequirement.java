package com.mycompany.storeinventorychecker;
public abstract class CategoryRequirement {
    /**
     * Shows all items in stock for a category
     */
    public abstract void showStock();
    
    /**
     * Shows items that are out of stock (stock = 0) for a category
     */
    public abstract void showNoStock();
}