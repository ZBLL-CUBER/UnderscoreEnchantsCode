package com.roughlyunderscore.enchs.listeners;

import com.roughlyunderscore.enchs.UnderscoreEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

// Just remove the player from gods if is present there
public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent ev) {
        if (UnderscoreEnchants.gods.containsKey(ev.getPlayer().getUniqueId())) {
            UnderscoreEnchants.gods.remove(ev.getPlayer().getUniqueId());
            ev.getPlayer().setInvulnerable(false);
        }
    }
}
