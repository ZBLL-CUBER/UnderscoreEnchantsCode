package com.roughlyunderscore.enchs.util.enums;

/*
The list of all the available permissions
 */
public enum Permissions {
    ENCHANT_GUI("enchantgui"),
    ANVIL_GUI("anvilgui"),
    LOG("log"),
    ENCHANT("enchant"),
    TOGGLE("toggle"),
    DOWNLOAD("download");

    private final String perm;
    Permissions(String node) {
        this.perm = "underscoreenchants." + node;
    }
    public String getPermission() {
        return this.perm;
    }

    @Override
    public String toString() {
        return this.perm;
    }
}
