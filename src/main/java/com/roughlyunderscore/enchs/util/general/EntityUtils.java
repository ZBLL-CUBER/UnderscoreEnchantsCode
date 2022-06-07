package com.roughlyunderscore.enchs.util.general;

import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.particles.XParticle;
import com.roughlyunderscore.enchs.UnderscoreEnchants;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Objects;

@UtilityClass
@SuppressWarnings("unused")
/*
Just a lot of utils that I don't want to explain
 */
public class EntityUtils {
	//<editor-fold desc="Location">
	public Location getLocation(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getLocation was called on entity " + entity.getName() + " and resulted in the following location: " + entity.getLocation());
		return entity.getLocation();
	}

	public double getX(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getX was called on entity " + entity.getName() + " and resulted in the following coordinate: " + getLocation(entity).getX());
		return getLocation(entity).getX();
	}
	public double getY(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getY was called on entity " + entity.getName() + " and resulted in the following coordinate: " + getLocation(entity).getY());
		return getLocation(entity).getY();
	}
	public double getZ(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getZ was called on entity " + entity.getName() + " and resulted in the following coordinate: " + getLocation(entity).getZ());
		return getLocation(entity).getZ();
	}
	public float getYaw(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getYaw was called on entity " + entity.getName() + " and resulted in the following angle: " + getLocation(entity).getYaw());
		return getLocation(entity).getYaw();
	}
	public float getPitch(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getPitch was called on entity " + entity.getName() + " and resulted in the following angle: " + getLocation(entity).getPitch());
		return getLocation(entity).getPitch();
	}

	public String getXString(Entity entity) {
		return String.valueOf(getX(entity));
	}
	public String getYString(Entity entity) {
		return String.valueOf(getY(entity));
	}
	public String getZString(Entity entity) {
		return String.valueOf(getZ(entity));
	}
	public String getYawString(Entity entity) {
		return String.valueOf(getYaw(entity));
	}
	public String getPitchString(Entity entity) {
		return String.valueOf(getPitch(entity));
	}

	public World getWorld(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("getWorld was called on entity " + entity.getName() + " and resulted in the following world: " + getLocation(entity).getWorld());
		return getLocation(entity).getWorld();
	}
	public String getWorldName(Entity entity) {
		return getWorld(entity).getName();
	}
	//</editor-fold>

	//<editor-fold desc="Health">
	public double getMaximumHealth(Entity entityy) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("getMaximumHealth was called on entity " + entity.getName() + " and resulted in the following value: " + Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
			return Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
		}
		return -1;
	}
	public String getMaximumHealthString(Entity entity) {
		return String.valueOf(getMaximumHealth(entity));
	}

	public double getHealth(Entity entityy) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("getHealth was called on entity " + entity.getName() + " and resulted in the following value: " + entity.getHealth());
			return entity.getHealth();
		}
		return 0;
	}
	public String getHealthString(Entity entity) {
		return String.valueOf(getHealth(entity));
	}
	//</editor-fold>

	//<editor-fold desc="Velocity">
	public void produceVelocity(Entity entity, double x, double y, double z) {
		UnderscoreEnchants.getStaticLogger().log("produceVelocity was called on entity " + entity.getName() + " with coordinates of " + x + ", " + y + " and " + z + ".");
		entity.setVelocity(new Vector(x, y, z));
	}
	public void produceVelocity(Entity entity, String x, String y, String z) {
		entity.setVelocity(new Vector(Utils.parseD(x), Utils.parseD(y), Utils.parseD(z)));
	}
	public void produceVelocity(Entity entity, String[] parameters) {
		produceVelocity(entity, parameters[1], parameters[2], parameters[3]);
	}
	//</editor-fold>

	//<editor-fold desc="Effects & potions">
	public void addPotion(Entity entityy, PotionEffect potion) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("addPotion was called on entity " + entity.getName() + " with the following potion: " + potion + ".");
			entity.addPotionEffect(potion);
		}
	}

	public void addPermanentPotion(Entity entityy, PotionEffectType effect) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("addPermanentPotion was called on entity " + entity.getName() + " with the following potion effect: " + effect + ".");
			entity.addPotionEffect(new PotionEffect(effect, 999999, 0));
		}
	}
	public void addPermanentPotion(Entity entity, XPotion effect) {
		addPermanentPotion(entity, effect.parsePotionEffectType());
	}
	public void addPermanentPotion(Entity entity, String effect) {
		addPermanentPotion(entity, XPotion.valueOf(effect).parsePotionEffectType());
	}

	public void addPermanentPotion(Entity entityy, PotionEffectType effect, int amplifier) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("addPermanentPotion was called on entity " + entity.getName() + " with effect " + effect + "and amplifier " + amplifier + ".");
			entity.addPotionEffect(new PotionEffect(effect, 999999, amplifier));
		}
	}
	public void addPermanentPotion(Entity entity, XPotion effect, int amplifier) {
		addPermanentPotion(entity, effect.parsePotionEffectType(), amplifier);
	}
	public void addPermanentPotion(Entity entity, String effect, int amplifier) {
		addPermanentPotion(entity, XPotion.valueOf(effect).parsePotionEffectType(), amplifier);
	}

	public void addPotion(Entity entityy, PotionEffectType effect, int ticks) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("addPermanentPotion was called on entity " + entity.getName() + " with effect " + effect + "and duration " + ticks + ".");
			entity.addPotionEffect(new PotionEffect(effect, ticks, 0));
		}
	}
	public void addPotion(Entity entity, XPotion effect, int ticks) {
		addPotion(entity, effect.parsePotionEffectType(), ticks, 0);
	}
	public void addPotion(Entity entity, String effect, int ticks) {
		addPotion(entity, XPotion.valueOf(effect).parsePotionEffectType(), ticks, 0);
	}

	public void addPotion(Entity entityy, PotionEffectType effect, int ticks, int amplifier) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("addPermanentPotion was called on entity " + entity.getName() + " with effect " + effect + ", duration " + ticks + "and amplifier " + amplifier + ".");
			entity.addPotionEffect(new PotionEffect(effect, ticks, amplifier));
		}
	}
	public void addPotion(Entity entity, XPotion effect, int ticks, int amplifier) {
		addPotion(entity, effect.parsePotionEffectType(), ticks, amplifier);
	}
	public void addPotion(Entity entity, String effect, int ticks, int amplifier) {
		addPotion(entity, XPotion.valueOf(effect).parsePotionEffectType(), ticks, amplifier);
	}

	public void removePotion(Entity entityy, PotionEffectType effect) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("removePotion was called on entity " + entity.getName() + " with the following potion effect: " + effect + ".");
			entity.removePotionEffect(effect);
		}
	}
	public void removePotion(Entity entity, XPotion effect) {
		removePotion(entity, effect.parsePotionEffectType());
	}
	public void removePotion(Entity entity, String effect) {
		removePotion(entity, XPotion.valueOf(effect).parsePotionEffectType());
	}

	public void removeBuffs(Entity entityy, UnderscoreEnchants plugin) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("removeBuffs was called on entity " + entity.getName() + ".");
			plugin.getPositiveEffects().forEach(entity::removePotionEffect);
		}
	}
	public void removeDebuffs(Entity entityy, UnderscoreEnchants plugin) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("removeDebuffs was called on entity " + entity.getName() + ".");
			plugin.getNegativeEffects().forEach(entity::removePotionEffect);
		}
	}
	//</editor-fold>

	//<editor-fold desc="Location">
	public void setLocation(Entity entity, Location location) {
		entity.teleport(location);
		UnderscoreEnchants.getStaticLogger().log("setLocation was called on entity " + entity.getName() + " with location of: " + location);
	}
	public void setLocation(Entity entity, double x, double y, double z) {
		setLocation(entity, x, y, z, getYaw(entity), getPitch(entity));
	}
	public void setLocation(Entity entity, double x, double y, double z, float yaw, float pitch) {
		setLocation(entity, new Location(getWorld(entity), x, y, z, yaw, pitch));
	}

	public void setX(Entity entity, double x) {
		setLocation(entity, x, getY(entity), getZ(entity));
	}
	public void increaseX(Entity entity, double adjustment) {
		setX(entity, getX(entity) + adjustment);
	}

	public void setY(Entity entity, double y) {
		setLocation(entity, getX(entity), y, getZ(entity));
	}
	public void increaseY(Entity entity, double adjustment) {
		setY(entity, getY(entity) + adjustment);
	}

	public void setZ(Entity entity, double z) {
		setLocation(entity, getX(entity), getY(entity), z);
	}
	public void increaseZ(Entity entity, double adjustment) {
		setZ(entity, getZ(entity) + adjustment);
	}

	public void sendForward(Entity entity, double adjustment) {
		UnderscoreEnchants.getStaticLogger().log("sendForward was called on entity " + entity.getName() + " with a value of " + adjustment + ".");
		Location loc = entity.getLocation().clone();
		Vector dir = loc.getDirection();
		dir.normalize();
		dir.multiply(adjustment);
		loc.add(dir);
		entity.teleport(loc);
	}
	public void sendForward(Entity entity, String adjustment) {
		sendForward(entity, Utils.parseD(adjustment));
	}

	public void setYaw(Entity entity, float yaw) {
		setLocation(entity, getX(entity), getY(entity), getX(entity), yaw, getPitch(entity));
	}
	public void increaseYaw(Entity entity, float adjustment) {
		setYaw(entity, getYaw(entity) + adjustment);
	}

	public void setPitch(Entity entity, float pitch) {
		setLocation(entity, getX(entity), getY(entity), getX(entity), getYaw(entity), pitch);
	}
	public void increasePitch(Entity entity, float adjustment) {
		setPitch(entity, getPitch(entity) + adjustment);
	}

	public void setDirection(Entity entity, float yaw, float pitch) {
		setYaw(entity, yaw);
		setPitch(entity, pitch);
	}
	public void setDirection(Entity entity, String yaw, String pitch) {
		setYaw(entity, Utils.parseF(yaw));
		setPitch(entity, Utils.parseF(pitch));
	}

	public void setWorld(Entity entity, World world) {
		UnderscoreEnchants.getStaticLogger().log("sendForward was called on entity " + entity.getName() + " with a world of " + world + ".");
		entity.teleport(world.getSpawnLocation());
	}
	public void setWorld(Entity entity, String name) {
		World world = (Bukkit.getWorld(name) == null ? Bukkit.createWorld(new WorldCreator(name)) : Bukkit.getWorld(name));
		setWorld(entity, Objects.requireNonNull(world));
	}

	public void spawnParticle(Entity entity, Location location, Particle particle) {
		UnderscoreEnchants.getStaticLogger().log("spawnParticle was called on location " + location + ", entity " + entity.getName() + " and particle " + particle.name());
		Location locatio = location.clone();
		locatio.setY(locatio.getY() + 1);
		Objects.requireNonNull(location.getWorld()).spawnParticle(particle, locatio, 1);
	}
	public void spawnParticle(Entity entity, Location location, String particle0) {
		spawnParticle(entity, location, XParticle.getParticle(particle0));
	}

	public void spawnParticleBoots(Entity entity, Location location, Particle particle) {
		UnderscoreEnchants.getStaticLogger().log("spawnParticleBoots was called on location " + location + ", entity " + entity.getName() + " and particle " + particle.name());
		Objects.requireNonNull(location.getWorld()).spawnParticle(particle, location, 1);
	}
	public void spawnParticleBoots(Entity entity, Location location, String particle0) {
		spawnParticleBoots(entity, location, XParticle.getParticle(particle0));
	}
	//</editor-fold>

	//<editor-fold desc="Health">
	public void setMaximumHealth(Entity entityy, double health) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("setMaximumHealth was called on entity " + entity.getName() + " with a value of " + health + ".");
			Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
		}
	}
	public void setMaximumHealth(Entity entity, String health) {
		setMaximumHealth(entity, Utils.parseD(health));
	}

	public void increaseMaximumHealth(Entity entity, double adjustment) {
		setMaximumHealth(entity, getMaximumHealth(entity) + adjustment);
	}
	public void increaseMaximumHealth(Entity entity, String adjustment) {
		increaseMaximumHealth(entity, Utils.parseD(adjustment));
	}

	public void setHealth(Entity entityy, double health) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("setHealth was called on entity " + entity.getName() + " with a value of " + health + ".");
			entity.setHealth(Math.max(0, Math.min(getMaximumHealth(entity), health))); // needs to be under the maximum value but above 0
		}
	}
	public void setHealth(Entity entity, String health) {
		setHealth(entity, Utils.parseD(health));
	}

	public void increaseHealth(Entity entity, double adjustment) {
		setHealth(entity, getHealth(entity) + adjustment);
	}
	public void increaseHealth(Entity entity, String adjustment) {
		increaseHealth(entity, Utils.parseD(adjustment));
	}
	//</editor-fold>

	//<editor-fold desc="Projectiles on entity's behalf">
	public void sendProjectile(Entity entityy, Class<? extends Projectile> clazz) {
		if (entityy instanceof ProjectileSource entity) {
			UnderscoreEnchants.getStaticLogger().log("sendProjectile was called for entity " + entityy.getName() + "with the projectile type of " + clazz.getName() + ".");
			entity.launchProjectile(clazz).setShooter(entity);
		}
	}
	public void sendArrow(Entity entity) {
		sendProjectile(entity, Arrow.class);
	}
	public void sendFireball(Entity entity) {
		sendProjectile(entity, Fireball.class);
	}
	//</editor-fold>

	//<editor-fold desc="Fire">
	public void setFire(Entity entity) {
		setFire(entity, 200);
	}
	public void setFire(Entity entity, int ticks) {
		UnderscoreEnchants.getStaticLogger().log("setFire was called for entity " + entity.getName() + "for " + ticks + "ticks.");
		entity.setFireTicks(ticks);
	}
	public void setFire(Entity entity, String ticks) {
		setFire(entity, Utils.parseI(ticks));
	}
	//</editor-fold>

	//<editor-fold desc="Entity state">
	public boolean swimming(Entity entityy) {
		if (entityy instanceof LivingEntity entity) {
			UnderscoreEnchants.getStaticLogger().log("boolean$swimming was called on entity " + entity.getName() + " and resulted in: " + entity.isSwimming());
			return entity.isSwimming();
		}
		return false;
	}

	public boolean onFire(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("onFire was called on entity " + entity.getName() + " and resulted in: " + (entity.getFireTicks() > 0));
		return entity.getFireTicks() > 0;
	}
	public boolean onTop(Entity entity) {
		// The Y of the entity (floored, e.g. 56.14 -> 56) must be equal or above the top block in the same location.
		boolean onTop = (int) Math.floor(getY(entity)) >= (int) Math.floor(getWorld(entity).getHighestBlockYAt(getLocation(entity)));

		UnderscoreEnchants.getStaticLogger().log("onTop was called on entity " + entity.getName() + " and resulted in: " + onTop);
		return onTop;
	}

	public boolean op(Entity entity) {
		UnderscoreEnchants.getStaticLogger().log("boolean$op was called on entity " + entity.getName() + " and resulted in: " + entity.isOp());
		return entity.isOp();
	}
	public int invisibleFor(Player player) {
		UnderscoreEnchants.getStaticLogger().log(String.format("invisibleFor was called for player %s and returned %d.", player.getName(), UnderscoreEnchants.gods.get(player.getUniqueId())));
		if (UnderscoreEnchants.gods.get(player.getUniqueId()) != null) return UnderscoreEnchants.gods.get(player.getUniqueId());
		else return 0;

	}

	public String invisibleForString(Player player) {
		if (UnderscoreEnchants.gods.get(player.getUniqueId()) != null) return String.valueOf(UnderscoreEnchants.gods.get(player.getUniqueId()));
		else return "0";
	}
	//</editor-fold>
}