package com.roughlyunderscore.enchs.util.holders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/*
Professionally misusing a piece of the API.
 */
public class AnvilHolder implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}
