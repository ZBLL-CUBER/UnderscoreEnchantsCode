package com.roughlyunderscore.enchs.util.data;

import com.roughlyunderscore.enchs.UnderscoreEnchants;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.roughlyunderscore.enchs.util.general.Utils.*;

@ToString @EqualsAndHashCode
/*
This is a way to quickly fetch the messages from the configuration.
 */
public class Messages {

    private UnderscoreEnchants enchants;

    public String NO_PERMS, NO_CONSOLE, CREATING_LOG, LOG_CREATED, NO_LOG, BAD_ARGS, WRONG_NAME, WRONG_LEVEL, ENCHANTED, ACTIVATED, DOWNLOADED, LOADED, WRONG_PARAMETER;

    public Messages(String string, UnderscoreEnchants enchants) {
        if (string.equals("Default")) {
            this.enchants = enchants;
            String prefix = format(enchants.getConfig().getString("prefix"));

            NO_PERMS = prefix + getMessage("no-perms");
            NO_CONSOLE = prefix + getMessage("no-console");
            CREATING_LOG = prefix + getMessage("creating-log");
            LOG_CREATED = prefix + getMessage("log-created");
            NO_LOG = prefix + getMessage("couldnt-make-log");
            BAD_ARGS = prefix + getMessage("improper-args");
            WRONG_NAME = prefix + getMessage("wrong-enchantment-name");
            WRONG_LEVEL = prefix + getMessage("wrong-enchantment-level");
            ENCHANTED = prefix + getMessage("enchanted");
            ACTIVATED = prefix + getMessage("activated");
            DOWNLOADED = prefix + getMessage("downloaded");
            LOADED = prefix + getMessage("loaded");
            WRONG_PARAMETER = prefix + getMessage("wrong-parameter");
        }
    }

    public Messages(UnderscoreEnchants enchants) {
        this("Default", enchants);
    }

    String getMessage(String arg) {
        return format(enchants.getConfig().getString("messages." + enchants.getConfig().getString("lang") + "." + arg));
    }
}
