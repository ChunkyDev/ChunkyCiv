package org.getchunky.actiontracker.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.getchunky.actiontracker.PersistenceInterface;
import org.getchunky.actiontracker.listener.BlockMonitor;
import org.getchunky.actiontracker.listener.EntityMonitor;
import org.getchunky.actiontracker.listener.PlayerMonitor;
import org.getchunky.actiontracker.listener.ServerMonitor;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunkyciv.util.MinecraftTools;

import java.util.HashSet;

/**
 * @author dumptruckman
 */
public class ActionTracker implements Runnable {

    /**
     * How often to track actions.  Default is one of each action every minute.  Going lower than this could decrease performance.
     */
    private final static long TICKS = 1200;

    private final static HashSet<Player> playerPlayerAttack = new HashSet<Player>();
    private final static HashSet<Player> playerMonsterAttack = new HashSet<Player>();
    private final static HashSet<Player> playerAttackedByPlayer = new HashSet<Player>();
    //private final static HashSet<Player> playerAttackByMonster = new HashSet<Player>();
    private final static HashSet<Player> playerPlace = new HashSet<Player>();
    private final static HashSet<Player> playerBreak = new HashSet<Player>();
    private final static HashSet<Player> playerChat = new HashSet<Player>();
    private final static HashSet<Player> playerAction = new HashSet<Player>();

    private static Plugin plugin;

    private static PersistenceInterface persistence;

    public ActionTracker(PersistenceInterface persistence) {
        ActionTracker.persistence = persistence;
    }

    public void run() {
        playerPlayerAttack.clear();
        playerMonsterAttack.clear();
        playerAttackedByPlayer.clear();
        //playerAttackByMonster.clear();
        playerPlace.clear();
        playerBreak.clear();
        playerChat.clear();
        playerAction.clear();
    }

    public static void schedule(final Plugin plugin, PersistenceInterface persistenceInterface) {
        ActionTracker.plugin = plugin;

        PluginManager pm = Bukkit.getServer().getPluginManager();

        EntityMonitor entityMonitor = new EntityMonitor();
        PlayerMonitor playerMonitor = new PlayerMonitor();
        BlockMonitor blockMonitor = new BlockMonitor();
        ServerMonitor serverMonitor = new ServerMonitor();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityMonitor, Event.Priority.Monitor, getPlugin());
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerMonitor, Event.Priority.Monitor, getPlugin());
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockMonitor, Event.Priority.Monitor, getPlugin());
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockMonitor, Event.Priority.Monitor, getPlugin());
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, serverMonitor, Event.Priority.Monitor, getPlugin());

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(
                getPlugin(),
                new ActionTracker(persistenceInterface),
                TICKS,
                TICKS);
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static void shutdown() {
        for (Player player : playerAction) {
            persistence.shutdown(player);
        }
    }

    private static void trackAction(Player player) {
        Boolean wasTracked = playerAction.contains(player);
        playerAction.add(player);

        if (!wasTracked) {
            persistence.actionTracked(player);
        }
    }

    public static void trackPlayerAttack(Player player) {
        Boolean wasTracked = playerPlayerAttack.contains(player);
        playerPlayerAttack.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.playerAttackTracked(player);
        }
    }

    public static void trackMonsterAttack(Player player) {
        Boolean wasTracked = playerMonsterAttack.contains(player);
        playerMonsterAttack.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.monsterAttackTracked(player);
        }
    }

    public static void trackAttackedByPlayer(Player player) {
        Boolean wasTracked = playerAttackedByPlayer.contains(player);
        playerAttackedByPlayer.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.playerAttackedByPlayerTracked(player);
        }
    }

    /*public static void trackAttackedByMonster(Player player) {
        Boolean wasTracked = playerAttackByMonster.contains(player);
        playerAttackByMonster.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.playerAttackedByMonsterTracked(player);
        }
    }*/

    public static void trackChat(Player player) {
        Boolean wasTracked = playerChat.contains(player);
        playerChat.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.chatTracked(player);
        }
    }

    public static void trackPlace(Player player) {
        Boolean wasTracked = playerPlace.contains(player);
        playerPlace.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.placeTracked(player);
        }
    }

    public static void trackBreak(Player player) {
        Boolean wasTracked = playerBreak.contains(player);
        playerBreak.add(player);

        trackAction(player);
        if (!wasTracked) {
            persistence.breakTracked(player);
        }
    }

}
