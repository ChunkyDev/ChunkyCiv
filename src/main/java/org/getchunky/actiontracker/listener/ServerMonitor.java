package org.getchunky.actiontracker.listener;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListener;
import org.getchunky.actiontracker.task.ActionTracker;

/**
 * @author dumptruckman
 */
public class ServerMonitor extends ServerListener {

    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(ActionTracker.getPlugin())) {
            ActionTracker.shutdown();
        }
    }
}
