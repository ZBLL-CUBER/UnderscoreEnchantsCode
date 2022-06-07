package com.roughlyunderscore.enchs.enchants;

import org.bukkit.enchantments.Enchantment;

import java.util.UUID;

/*
do i have to explain this?
 */
public class Cooldown {
	private long seconds;
	private final Enchantment enchantment;
	private final UUID uuid;

	public Cooldown(long seconds, Enchantment enchantment, UUID uuid) {
		this.seconds = seconds;
		this.enchantment = enchantment;
		this.uuid = uuid;
	}

	/**
	 * @return true if cooldown is over
	 */
	public boolean decrease() {
		if (seconds == 1) return true;
		else seconds--;
		return false;
	}

	public Enchantment getEnchantment() {
		return this.enchantment;
	}
	public UUID getUUID() {
		return this.uuid;
	}
}
