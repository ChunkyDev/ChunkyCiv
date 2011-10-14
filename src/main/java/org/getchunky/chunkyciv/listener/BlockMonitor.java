package org.getchunky.chunkyciv.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getchunky.chunkyciv.task.ActionTracker;

/**
 * @author dumptruckman
 */
public class BlockMonitor extends BlockListener {

    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        playerPlacing(event.getPlayer());
    }

    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        playerBreaking(event.getPlayer());
    }

    private void playerPlacing(Player player) {
        ActionTracker.trackPlace(player);
    }

    private void playerBreaking(Player player) {
        ActionTracker.trackBreak(player);
    }
}
