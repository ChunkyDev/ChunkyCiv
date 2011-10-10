package org.getchunky.chunkyciv;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getchunky.chunky.exceptions.ChunkyUnregisteredException;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunkyciv.command.CmdNation;
import org.getchunky.chunkyciv.command.CmdTown;
import org.getchunky.chunkyciv.config.Config;
import org.getchunky.chunkyciv.locale.Language;
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

        // Register commands
        registerCommands();

        //Call Home (usage stats)
        CallHome.load(this);

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    private void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        // Event registering goes here
    }

    private void registerCommands() {
        try {
            ChunkyCommand town = new ChunkyCommand("town", new CmdTown(), null)
                    .setAliases("t")
                    .setHelpLines(Language.getStrings(Language.CMD_TOWN_HELP))
                    .register();

            ChunkyCommand nation = new ChunkyCommand("nation", new CmdNation(), null)
                    .setAliases("n")
                    .setHelpLines(Language.getStrings(Language.CMD_NATION_HELP))
                    .register();
        } catch (ChunkyUnregisteredException ignore) {}
    }

    public static ChunkyCiv getInstance() {
        return instance;
    }
}
