package com.roughlyunderscore.enchs.parsers;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.cryptomorin.xseries.XMaterial;
import com.roughlyunderscore.enchs.UnderscoreEnchants;
import com.roughlyunderscore.enchs.events.*;
import com.roughlyunderscore.enchs.util.general.Utils;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Locale;

import static com.roughlyunderscore.enchs.util.general.PlayerUtils.*;
import static com.roughlyunderscore.enchs.util.general.Utils.*;
import static com.roughlyunderscore.enchs.util.general.EntityUtils.*;

@UtilityClass
public class ConditionParsers {

	// This is one of the core parts of parsing an enchantment!

	// Required for a preparatory parser
	public boolean parseCondition(Event event0, String condition, UnderscoreEnchants plugin) {
		if (event0 instanceof PlayerPVPEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof ArmorEquipEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof BlockBreakEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerItemBreakEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerItemConsumeEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerInteractAtEntityEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerInteractEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerMoveEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerGotHurtEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerHurtsEntityEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerShootBowEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerToggleSneakEvent event) return parseCondition(event, condition, plugin);
		else if (event0 instanceof PlayerBowHitEvent event) return parseCondition(event, condition, plugin);
		return false;
	}

	public boolean parseCondition(PlayerPVPEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getDamager();
		Player vic = ev.getVictim();
		double damage = ev.getDamage();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}



		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "victim-sneaking" -> sneaking(vic);
			case "victim-sprinting" -> sprinting(vic);
			case "victim-swimming" -> swimming(vic);
			case "victim-blocking" -> blocking(vic);
			case "victim-flying" -> flying(vic);

			case "victim-onfire" -> onFire(vic);
			case "victim-onhighestblock" -> onTop(vic);

			case "victim-rain" -> rains(vic);
			case "victim-clear" -> sunshines(vic);
			case "victim-thunder" -> thunders(vic);

			case "victim-day" -> day(vic);
			case "victim-night" -> night(vic);

			case "victim-overworld" -> overworld(vic);
			case "victim-nether" -> nether(vic);
			case "victim-end" -> end(vic);

			case "victim-op" -> op(vic);

			case "victim-health-of" -> (int) getHealth(vic) == Utils.parseD(condition[1]);
			case "victim-health-lower" -> getHealth(vic) < Utils.parseD(condition[1]);
			case "victim-health-higher" -> getHealth(vic) > Utils.parseD(condition[1]);
			case "victim-healthy" -> getHealth(vic) == getMaximumHealth(vic);

			case "victim-food-of" -> getFood(vic) == Utils.parseI(condition[1]);
			case "victim-food-lower" -> getFood(vic) < Utils.parseI(condition[1]);
			case "victim-food-higher" -> getFood(vic) > Utils.parseI(condition[1]);
			case "victim-satiated" -> getFood(vic) == 20;

			case "victim-air-of" -> getAir(vic) == Utils.parseI(condition[1]);
			case "victim-air-lower" -> getAir(vic) < Utils.parseI(condition[1]);
			case "victim-air-higher" -> getAir(vic) > Utils.parseI(condition[1]);
			case "victim-oxygenated" -> getAir(vic) == getMaximumAir(vic);

			case "victim-godmode-of" -> invisibleFor(vic) == Utils.parseI(condition[1]);
			case "victim-godmode-lower" -> invisibleFor(vic) < Utils.parseI(condition[1]);
			case "victim-godmode-higher" -> invisibleFor(vic) > Utils.parseI(condition[1]);

			case "damage-of" -> Math.floor(damage) == Utils.parseD(condition[1]);
			case "damage-lower" -> damage < Utils.parseD(condition[1]);
			case "damage-higher" -> damage > Utils.parseD(condition[1]);
			case "damage-lethal" -> damage >= getHealth(vic);
			case "damage-non-lethal" -> damage < getHealth(vic);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerBowHitEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getDamager();
		Player vic = ev.getVictim();
		double damage = ev.getDamage();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "victim-sneaking" -> sneaking(vic);
			case "victim-sprinting" -> sprinting(vic);
			case "victim-swimming" -> swimming(vic);
			case "victim-blocking" -> blocking(vic);
			case "victim-flying" -> flying(vic);

			case "victim-onfire" -> onFire(vic);
			case "victim-onhighestblock" -> onTop(vic);

			case "victim-rain" -> rains(vic);
			case "victim-clear" -> sunshines(vic);
			case "victim-thunder" -> thunders(vic);

			case "victim-day" -> day(vic);
			case "victim-night" -> night(vic);

			case "victim-overworld" -> overworld(vic);
			case "victim-nether" -> nether(vic);
			case "victim-end" -> end(vic);

			case "victim-op" -> op(vic);

			case "victim-health-of" -> (int) getHealth(vic) == Utils.parseD(condition[1]);
			case "victim-health-lower" -> getHealth(vic) < Utils.parseD(condition[1]);
			case "victim-health-higher" -> getHealth(vic) > Utils.parseD(condition[1]);
			case "victim-healthy" -> getHealth(vic) == getMaximumHealth(vic);

			case "victim-food-of" -> getFood(vic) == Utils.parseI(condition[1]);
			case "victim-food-lower" -> getFood(vic) < Utils.parseI(condition[1]);
			case "victim-food-higher" -> getFood(vic) > Utils.parseI(condition[1]);
			case "victim-satiated" -> getFood(vic) == 20;

			case "victim-air-of" -> getAir(vic) == Utils.parseI(condition[1]);
			case "victim-air-lower" -> getAir(vic) < Utils.parseI(condition[1]);
			case "victim-air-higher" -> getAir(vic) > Utils.parseI(condition[1]);
			case "victim-oxygenated" -> getAir(vic) == getMaximumAir(vic);

			case "victim-godmode-of" -> invisibleFor(vic) == Utils.parseI(condition[1]);
			case "victim-godmode-lower" -> invisibleFor(vic) < Utils.parseI(condition[1]);
			case "victim-godmode-higher" -> invisibleFor(vic) > Utils.parseI(condition[1]);

			case "damage-of" -> Math.floor(damage) == Utils.parseD(condition[1]);
			case "damage-lower" -> damage < Utils.parseD(condition[1]);
			case "damage-higher" -> damage > Utils.parseD(condition[1]);
			case "damage-lethal" -> damage >= getHealth(vic);
			case "damage-non-lethal" -> damage < getHealth(vic);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(ArmorEquipEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		Material oldType = ev.getOldArmorPiece() == null ? Material.AIR : ev.getOldArmorPiece().getType(),
				 newType = ev.getNewArmorPiece() == null ? Material.AIR : ev.getNewArmorPiece().getType();
		ItemStack oldItem = ev.getOldArmorPiece(),
				  newItem = ev.getNewArmorPiece();
		String oldName = oldItem == null ? "" : (oldItem.getItemMeta() == null ? "" : oldItem.getItemMeta().getDisplayName().toLowerCase(Locale.ROOT)),
			   newName = newItem == null ? "" : (newItem.getItemMeta() == null ? "" : newItem.getItemMeta().getDisplayName().toLowerCase(Locale.ROOT));

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "equipped-helmet" -> newItem != null && newType != Material.AIR && (newName.contains("helmet"));
			case "equipped-chestplate" -> newItem != null && newType != Material.AIR && newName.contains("chestplate");
			case "equipped-leggings" -> newItem != null && newType != Material.AIR && newName.contains("leggings");
			case "equipped-boots" -> newItem != null && newType != Material.AIR && newName.contains("boots");
			case "equipped" -> newItem != null && newType != Material.AIR;

			case "unequipped-helmet" -> oldItem != null && oldType != Material.AIR && (oldName.contains("helmet"));
			case "unequipped-chestplate" -> oldItem != null && oldType != Material.AIR && oldName.contains("chestplate");
			case "unequipped-leggings" -> oldItem != null && oldType != Material.AIR && oldName.contains("leggings");
			case "unequipped-boots" -> oldItem != null && oldType != Material.AIR && oldName.contains("boots");
			case "unequipped" -> oldItem != null && oldType != Material.AIR;

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(BlockBreakEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		Block block = ev.getBlock();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "block-is" -> block.getType().name().equalsIgnoreCase(condition[1]);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerItemBreakEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		ItemStack item = ev.getBrokenItem();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "item-is" -> item.getType().name().equalsIgnoreCase(condition[1]);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerItemConsumeEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		ItemStack item = ev.getItem();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "food-is" -> item.getType().name().equalsIgnoreCase(condition[1]);
			case "is-vegetarian" -> isVegetarian(item.getType());
			case "is-pescetarian" -> isPescetarian(item.getType());
			case "is-potion" -> item.getType() == Material.POTION;
			case "is-honey" -> item.getType() == Material.HONEY_BOTTLE;
			case "is-milk" -> item.getType() == Material.MILK_BUCKET;
			case "is-food" -> !(parseCondition(ev, "is-potion", plugin) || parseCondition(ev, "is-milk", plugin) || parseCondition(ev, "is-honey", plugin));

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerInteractAtEntityEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		Entity entity = ev.getRightClicked();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "entity-swimming" -> swimming(entity);
			case "entity-onfire" -> onFire(entity);
			case "entity-onhighestblock" -> onTop(entity);

			case "entity-health-of" -> (int) getHealth(entity) == Utils.parseD(condition[1]);
			case "entity-health-lower" -> getHealth(entity) < Utils.parseD(condition[1]);
			case "entity-health-higher" -> getHealth(entity) > Utils.parseD(condition[1]);
			case "entity-healthy" -> getHealth(entity) == getMaximumHealth(entity);
			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerInteractEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		Player player = ev.getPlayer();
		Location location = player.getLocation(); assert location.getWorld() != null; // this is for my IDE to stop freaking out, will always be true
		Block block = ev.getClickedBlock() == null ? location.getWorld().getBlockAt(0, 0, 0) : ev.getClickedBlock(); // null check

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "block-is" -> block.getType().name().equalsIgnoreCase(condition[1]);
			case "clicked-lmb-air" -> ev.getAction() == Action.LEFT_CLICK_AIR;
			case "clicked-lmb-block" -> ev.getAction() == Action.LEFT_CLICK_BLOCK;
			case "clicked-rmb-air" -> ev.getAction() == Action.RIGHT_CLICK_AIR;
			case "clicked-rmb-block" -> ev.getAction() == Action.RIGHT_CLICK_BLOCK;
			case "physical-action" -> ev.getAction() == Action.PHYSICAL;
			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerMoveEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();
		Location from = ev.getFrom().clone().subtract(0, 1, 0);
		Location to = (ev.getTo() == null ? ev.getFrom() : ev.getTo()).clone().subtract(0, 1, 0); // null check

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "from-is" -> from.getBlock().getType() == XMaterial.valueOf(condition[1]).parseMaterial();
			case "to-is" -> to.getBlock().getType() == XMaterial.valueOf(condition[1]).parseMaterial();
			case "jump" -> isByJump(ev);
			case "same-block" -> isBySameBlock(ev);
			case "not-same-block" -> isByDifferentBlocks(ev);
			case "head-rotate" -> isByHeadRotate(ev);
			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerGotHurtEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getVictim();
		double damage = ev.getDamage();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "damage-of" -> Math.floor(damage) == Utils.parseD(condition[1]);
			case "damage-lower" -> damage < Utils.parseD(condition[1]);
			case "damage-higher" -> damage > Utils.parseD(condition[1]);
			case "damage-lethal" -> damage >= getHealth(pl);
			case "damage-non-lethal" -> damage < getHealth(pl);

			case "caused-by-block-explosion" ->         ev.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
			case "caused-by-hazardous-block" ->         ev.getCause() == EntityDamageEvent.DamageCause.CONTACT;
			case "caused-by-entity-cramming" ->         ev.getCause() == EntityDamageEvent.DamageCause.CRAMMING;
			case "caused-by-unknown-source" ->          ev.getCause() == EntityDamageEvent.DamageCause.CUSTOM;
			case "caused-by-dragon-breath" ->           ev.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH;
			case "caused-by-drowning" ->                ev.getCause() == EntityDamageEvent.DamageCause.DROWNING;
			case "caused-by-entity-attack" ->           ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK;
			case "caused-by-entity-explosion" ->        ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
			case "caused-by-entity-sweep-attack" ->     ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK;
			case "caused-by-falling" ->                 ev.getCause() == EntityDamageEvent.DamageCause.FALL;
			case "caused-by-falling-block" ->           ev.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK;
			case "caused-by-direct-fire-exposure" ->    ev.getCause() == EntityDamageEvent.DamageCause.FIRE;
			case "caused-by-fire-tick" ->               ev.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK;
			case "caused-by-flying-into-wall" ->        ev.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL;
			case "caused-by-freezing" ->                ev.getCause() == EntityDamageEvent.DamageCause.FREEZE;
			case "caused-by-magma-damage" ->            ev.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR;
			case "caused-by-lava" ->                    ev.getCause() == EntityDamageEvent.DamageCause.LAVA;
			case "caused-by-lightning" ->               ev.getCause() == EntityDamageEvent.DamageCause.LIGHTNING;
			case "caused-by-magic" ->                   ev.getCause() == EntityDamageEvent.DamageCause.MAGIC;
			case "caused-by-poison" ->                  ev.getCause() == EntityDamageEvent.DamageCause.POISON;
			case "caused-by-projectile" ->              ev.getCause() == EntityDamageEvent.DamageCause.PROJECTILE;
			case "caused-by-starvation" ->              ev.getCause() == EntityDamageEvent.DamageCause.STARVATION;
			case "caused-by-suffocation" ->             ev.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION;
			case "caused-by-plugin-enforcement" ->      ev.getCause() == EntityDamageEvent.DamageCause.SUICIDE;
			case "caused-by-thorns" ->                  ev.getCause() == EntityDamageEvent.DamageCause.THORNS;
			case "caused-by-void" ->                    ev.getCause() == EntityDamageEvent.DamageCause.VOID;
			case "caused-by-withering" ->               ev.getCause() == EntityDamageEvent.DamageCause.WITHER;


			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerHurtsEntityEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getDamager();
		Entity entity = ev.getEntity();
		double damage = ev.getDamage();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "entity-swimming" -> swimming(entity);
			case "entity-onfire" -> onFire(entity);
			case "entity-onhighestblock" -> onTop(entity);

			case "entity-health-of" -> (int) getHealth(entity) == Utils.parseD(condition[1]);
			case "entity-health-lower" -> getHealth(entity) < Utils.parseD(condition[1]);
			case "entity-health-higher" -> getHealth(entity) > Utils.parseD(condition[1]);
			case "entity-healthy" -> getHealth(entity) == getMaximumHealth(entity);

			case "damage-of" -> Math.floor(damage) == Utils.parseD(condition[1]);
			case "damage-lower" -> damage < Utils.parseD(condition[1]);
			case "damage-higher" -> damage > Utils.parseD(condition[1]);
			case "damage-lethal" -> damage >= getHealth(entity);
			case "damage-non-lethal" -> damage < getHealth(entity);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerShootBowEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getShooter();
		float force = ev.getForce();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "force-of" -> Float.parseFloat(new DecimalFormat("#.#").format(force)) == Utils.parseD(condition[1]);
			case "force-lower" -> force < Utils.parseD(condition[1]);
			case "force-higher" -> force > Utils.parseD(condition[1]);

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}

	public boolean parseCondition(PlayerToggleSneakEvent ev, String condition0, UnderscoreEnchants plugin) {
		if (condition0 == null || condition0.isEmpty() || condition0.isBlank()) return true;
		String[] condition = condition0.split(" ");

		Player pl = ev.getPlayer();

		boolean negate = false;
		if (condition[0].startsWith("!")) {
			condition[0] = condition[0].substring(1);
			negate = true;
		}

		boolean result = switch (condition[0].toLowerCase()) {
			case "sneaking" -> sneaking(pl);
			case "sprinting" -> sprinting(pl);
			case "swimming" -> swimming(pl);
			case "blocking" -> blocking(pl);
			case "flying" -> flying(pl);

			case "onfire" -> onFire(pl);
			case "onhighestblock" -> onTop(pl);

			case "rain" -> rains(pl);
			case "clear" -> sunshines(pl);
			case "thunder" -> thunders(pl);

			case "day" -> day(pl);
			case "night" -> night(pl);

			case "overworld" -> overworld(pl);
			case "nether" -> nether(pl);
			case "end" -> end(pl);

			case "op" -> op(pl);

			case "health-of" -> (int) getHealth(pl) == Utils.parseD(condition[1]);
			case "health-lower" -> getHealth(pl) < Utils.parseD(condition[1]);
			case "health-higher" -> getHealth(pl) > Utils.parseD(condition[1]);
			case "healthy" -> getHealth(pl) == getMaximumHealth(pl);

			case "food-of" -> getFood(pl) == Utils.parseI(condition[1]);
			case "food-lower" -> getFood(pl) < Utils.parseI(condition[1]);
			case "food-higher" -> getFood(pl) > Utils.parseI(condition[1]);
			case "satiated" -> getFood(pl) == 20;

			case "air-of" -> getAir(pl) == Utils.parseI(condition[1]);
			case "air-lower" -> getAir(pl) < Utils.parseI(condition[1]);
			case "air-higher" -> getAir(pl) > Utils.parseI(condition[1]);
			case "oxygenated" -> getAir(pl) == getMaximumAir(pl);

			case "godmode-of" -> invisibleFor(pl) == Utils.parseI(condition[1]);
			case "godmode-lower" -> invisibleFor(pl) < Utils.parseI(condition[1]);
			case "godmode-higher" -> invisibleFor(pl) > Utils.parseI(condition[1]);

			case "sneaked" -> ev.isSneaking();
			case "unsneaked" -> !ev.isSneaking();

			default -> false;

		};

		if (negate) return !result;
		else return result;
	}
}
