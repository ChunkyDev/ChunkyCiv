package org.getchunky.chunkyciv;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getchunky.chunky.Chunky;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.exceptions.ChunkyUnregisteredException;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyPermissions;
import org.getchunky.chunky.permission.PermissionFlag;
import org.getchunky.chunkyciv.command.*;
import org.getchunky.chunkyciv.config.Config;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.permission.Perm;
import org.getchunky.chunkyciv.util.Logging;

import java.io.IOException;

/**
 * @author dumptruckman
 */
public class ChunkyCiv extends JavaPlugin {

    private static ChunkyCiv instance = null;

    final public void onDisable() {
        // Display disable message/version info
        Logging.info("disabled.", true);
    }

    final public void onEnable() {
        // Store the instance of this plugin
        instance = this;

        // Grab the PluginManager
        final PluginManager pm = getServer().getPluginManager();

        // Loads the configuration
        try {
            Config.load();
        } catch (IOException e) {  // Catch errors loading the config file and exit out if found.
            Logging.severe("Encountered an error while loading the configuration file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the language
        try {
            Language.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the language file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Register Events
        registerEvents();
        registerChunkyEvents();

        // Register commands
        registerCommands();

        // Register custom Chunky permissions
        registerChunkyPermissions();

        //Call Home (usage stats)
        CallHome.load(this);

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    private void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        // Event registering goes here
    }

    private void registerChunkyEvents() {
        
    }

    private void registerCommands() {
        try {
            ChunkyCommand town = new ChunkyCommand("town", new CmdTown(), null)
                    .setAliases("t")
                    .setHelpLines(Language.getStrings(Language.CMD_TOWN_HELP))
                    .register();

            ChunkyCommand civ = new ChunkyCommand("civ", new CmdCiv(), null)
                    .setAliases("cv", "civilization")
                    .setHelpLines(Language.getStrings(Language.CMD_CIV_HELP))
                    .setInGameOnly(true)
                    .register();

            ChunkyCommand civNew = new ChunkyCommand("new", new CmdCivNew(), civ)
                    .setAliases("create")
                    .setDescription(Language.CMD_CIV_NEW_DESC.getString())
                    .setHelpLines(Language.CMD_CIV_NEW_HELP.getStrings())
                    .setInGameOnly(true)
                    .setPermission(Perm.NATION_CREATE.getPermission())
                    .register();

            ChunkyCommand civClaim = new ChunkyCommand("claim", new CmdCivClaim(), civ)
                    .setAliases("c")
                    .setDescription(Language.CMD_CIV_CLAIM_DESC.getString())
                    .setHelpLines(Language.CMD_CIV_CLAIM_HELP.getStrings())
                    .setInGameOnly(true)
                    //.setPermission(Perm.NATION_CLAIM.getPermission())
                    .register();

            ChunkyCommand civUnclaim = new ChunkyCommand("unclaim", new CmdCivUnclaim(), civ)
                    .setAliases("uc", "u")
                    .setDescription(Language.CMD_CIV_UNCLAIM_DESC.getString())
                    .setHelpLines(Language.CMD_CIV_UNCLAIM_HELP.getStrings())
                    .setInGameOnly(true)
                    //.setPermission(Perm.NATION_UNCLAIM.getPermission())
                    .register();
        } catch (ChunkyUnregisteredException ignore) {}
    }

    private void registerChunkyPermissions() {
        ChunkyPermissions.registerPermissionFlag(CivManager.CIV_CLAIM);
        ChunkyPermissions.registerPermissionFlag(CivManager.CIV_UNCLAIM);
    }

    public static ChunkyCiv getInstance() {
        return instance;
    }
}
