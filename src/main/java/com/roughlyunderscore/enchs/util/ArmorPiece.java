package com.roughlyunderscore.enchs.util;

import lombok.Data;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Data
public class ArmorPiece {
	private final EquipmentSlot slot;
	private final ItemStack item;

	public static ArmorPiece of(EquipmentSlot slot, ItemStack item) {
		return new ArmorPiece(slot, item);
	}
}
