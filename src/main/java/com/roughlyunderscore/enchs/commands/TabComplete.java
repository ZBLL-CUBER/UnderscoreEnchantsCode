package com.roughlyunderscore.enchs.commands;

import com.roughlyunderscore.enchs.UnderscoreEnchants;
import static com.roughlyunderscore.enchs.util.general.Utils.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
/*
This is the tabcompleter for the only UnderscoreEnchants command.
It contains everything except for the easter egg.
 */
public class TabComplete implements TabCompleter {
    private final UnderscoreEnchants plugin;

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        if (args == null) return null;
        if (args.length == 1) {
            return Arrays.asList("log", "anvil", "enchanttable", "enchant", "toggle", "download");
        }
        if (args[0].equals("enchant") || args[0].equals("toggle")) {
            List<String> list = new ArrayList<>();
            plugin.getEnchantmentData().forEach(ench -> list.add(ench.getCommandName()));
            Arrays.stream(Enchantment.values()).forEach(ench -> list.add(getName(ench).replace(" ", "_").toLowerCase(Locale.ROOT)));
            return list;
        }
        return null;
    }
}
