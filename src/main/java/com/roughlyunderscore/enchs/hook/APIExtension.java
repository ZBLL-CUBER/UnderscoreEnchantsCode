package com.roughlyunderscore.enchs.hook;

import com.roughlyunderscore.enchantsapi.UEnchantsAPI;
import com.roughlyunderscore.enchs.UnderscoreEnchants;
import com.roughlyunderscore.enchs.util.data.DetailedEnchantment;

import static com.roughlyunderscore.enchs.util.general.Utils.*;

import com.roughlyunderscore.enchs.util.general.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class APIExtension implements UEnchantsAPI {

	private ItemStack enchant0(ItemStack it, Enchantment en, int lvl) {
		return Utils.enchant(it, en, lvl).getKey();
	}

	@Override
	public ItemStack enchant(ItemStack itemStack, String name, int level) throws IllegalArgumentException {
		DetailedEnchantment enchantment = parseEnchantment(name, level, false);
		if (enchantment.equals(UnderscoreEnchants.STATIC_EMPTY)) {
			throw new IllegalArgumentException(String.format("The enchantment wasn't found or the level is invalid! Name: %s, level: %d", name, level));
		}

		return enchant0(itemStack, enchantment.getEnchantment(), level);
	}

	@Override
	public ItemStack enchant(ItemStack itemStack, NamespacedKey namespacedKey, int level) throws IllegalArgumentException {
		DetailedEnchantment ench = new DetailedEnchantment(namespacedKey);
		if (ench.getEnchantment() == null) {
			throw new IllegalArgumentException(String.format("The enchantment wasn't found! Key: %s", namespacedKey.getKey()));
		}

		if (level < ench.getEnchantment().getStartLevel() || level > ench.getEnchantment().getMaxLevel()) {
			throw new IllegalArgumentException(String.format("The enchantment's level is invalid! Key: %s", namespacedKey.getKey()));
		}

		return enchant0(itemStack, ench.getEnchantment(), level);
	}

	@Override
	public ItemStack enchant(ItemStack itemStack, Enchantment enchantment, int level) throws IllegalArgumentException {
		if (level < enchantment.getStartLevel() || level > enchantment.getMaxLevel()) {
			throw new IllegalArgumentException(String.format("The enchantment's level is invalid! Name: %s", enchantment.getName()));
		}

		return enchant0(itemStack, enchantment, level);
	}

	@Override
	public void enchant(Player player, EquipmentSlot equipmentSlot, String name, int level) throws IllegalArgumentException {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchant(itemStack, name, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}

	@Override
	public void enchant(Player player, EquipmentSlot equipmentSlot, NamespacedKey namespacedKey, int level) throws IllegalArgumentException {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchant(itemStack, namespacedKey, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}

	@Override
	public void enchant(Player player, EquipmentSlot equipmentSlot, Enchantment enchantment, int level) throws IllegalArgumentException {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchant(itemStack, enchantment, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}




	@Override
	public ItemStack enchantUnrestricted(ItemStack itemStack, String name, int level) throws IllegalArgumentException {
		DetailedEnchantment enchantment = parseEnchantment(name, level, true);
		if (enchantment.equals(UnderscoreEnchants.STATIC_EMPTY)) {
			throw new IllegalArgumentException(String.format("The enchantment wasn't found or the level is invalid! Name: %s, level: %d", name, level));
		}

		return enchant0(itemStack, enchantment.getEnchantment(), level);
	}

	@Override
	public ItemStack enchantUnrestricted(ItemStack itemStack, NamespacedKey namespacedKey, int level) throws IllegalArgumentException {
		DetailedEnchantment ench = new DetailedEnchantment(namespacedKey);
		if (ench.getEnchantment() == null) {
			throw new IllegalArgumentException(String.format("The enchantment wasn't found! Key: %s", namespacedKey.getKey()));
		}

		return enchant0(itemStack, ench.getEnchantment(), level);
	}

	@Override
	public ItemStack enchantUnrestricted(ItemStack itemStack, Enchantment enchantment, int level) {
		return enchant0(itemStack, enchantment, level);
	}

	@Override
	public void enchantUnrestricted(Player player, EquipmentSlot equipmentSlot, String name, int level) throws IllegalArgumentException {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchantUnrestricted(itemStack, name, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}

	@Override
	public void enchantUnrestricted(Player player, EquipmentSlot equipmentSlot, NamespacedKey namespacedKey, int level) throws IllegalArgumentException {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchantUnrestricted(itemStack, namespacedKey, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}

	@Override
	public void enchantUnrestricted(Player player, EquipmentSlot equipmentSlot, Enchantment enchantment, int level) {
		ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
		itemStack = enchantUnrestricted(itemStack, enchantment, level);
		if (itemStack != null) {
			player.getInventory().setItem(equipmentSlot, itemStack);
		}
	}
}
