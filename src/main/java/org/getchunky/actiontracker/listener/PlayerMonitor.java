package org.getchunky.actiontracker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.getchunky.actiontracker.task.ActionTracker;

/**
 * @author dumptruckman
 */
public class PlayerMonitor extends PlayerListener {

    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) return;

        playerChatting(event.getPlayer());
    }

    public void playerChatting(Player player) {
        ActionTracker.trackChat(player);
    }
}
