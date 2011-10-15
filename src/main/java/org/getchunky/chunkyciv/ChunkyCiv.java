package org.getchunky.chunkyciv;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getchunky.chunky.Chunky;
import org.getchunky.chunky.event.ChunkyEvent;
import org.getchunky.chunky.exceptions.ChunkyUnregisteredException;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyPermissions;
import org.getchunky.chunkyciv.command.*;
import org.getchunky.chunkyciv.config.Config;
import org.getchunky.chunkyciv.listener.BlockMonitor;
import org.getchunky.chunkyciv.listener.ChunkyPlayerEvents;
import org.getchunky.chunkyciv.listener.EntityMonitor;
import org.getchunky.chunkyciv.listener.PlayerMonitor;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.permission.Perm;
import org.getchunky.chunkyciv.task.ActionTracker;
import org.getchunky.chunkyciv.util.Logging;
import org.getchunky.chunkyciv.util.MinecraftTools;

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

        // initialize the logger.
        Logging.load();

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

        // Register scheduled tasks
        registerTasks();

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
        EntityMonitor entityMonitor = new EntityMonitor();
        PlayerMonitor playerMonitor = new PlayerMonitor();
        BlockMonitor blockMonitor = new BlockMonitor();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityMonitor, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerMonitor, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockMonitor, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockMonitor, Event.Priority.Monitor, this);
    }

    private void registerChunkyEvents() {
        Chunky.getModuleManager().registerEvent(ChunkyEvent.Type.PLAYER_CHUNK_CHANGE, new ChunkyPlayerEvents(), ChunkyEvent.Priority.Normal, this);
    }

    private void registerTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(
                this,
                new ActionTracker(),
                MinecraftTools.convertSecondsToTicks(60),
                MinecraftTools.convertSecondsToTicks(60));
    }

    private void registerCommands() {
        try {
            ChunkyCommand town = new ChunkyCommand("town", new CmdTown(), null)
                    .setAliases("t")
                    .setHelpLines(Language.getStrings(Language.CMD_TOWN_HELP))
                    .register();

            ChunkyCommand nation = new ChunkyCommand("nation", new CmdNation(), null)
                    .setAliases("nat", "n")
                    .setHelpLines(Language.getStrings(Language.CMD_NAT_HELP))
                    .setInGameOnly(true)
                    .register();

            ChunkyCommand nationNew = new ChunkyCommand("new", new CmdNationNew(), nation)
                    .setAliases("create")
                    .setDescription(Language.CMD_NAT_NEW_DESC.getString())
                    .setHelpLines(Language.CMD_NAT_NEW_HELP.getStrings())
                    .setInGameOnly(true)
                    .setPermission(Perm.NATION_CREATE.getPermission())
                    .register();

            ChunkyCommand nationClaim = new ChunkyCommand("claim", new CmdNationClaim(), nation)
                    .setAliases("c")
                    .setDescription(Language.CMD_NAT_CLAIM_DESC.getString())
                    .setHelpLines(Language.CMD_NAT_CLAIM_HELP.getStrings())
                    .setInGameOnly(true)
                    //.setPermission(Perm.NATION_CLAIM.getPermission())
                    .register();

            ChunkyCommand nationUnclaim = new ChunkyCommand("unclaim", new CmdNationUnclaim(), nation)
                    .setAliases("uc", "u")
                    .setDescription(Language.CMD_NAT_UNCLAIM_DESC.getString())
                    .setHelpLines(Language.CMD_NAT_UNCLAIM_HELP.getStrings())
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
