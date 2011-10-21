package org.getchunky.actiontracker;

import org.bukkit.entity.Player;

/**
 * @author dumptruckman
 */
public interface PersistenceInterface {

    /**
     * "total actions" are only tracked once per minute for one of all types of different actions.
     * In other words, if a player attacks and chats within the same minute, it still only counts as 1 total action.
     *
     * @param player player performing the action
     */
    public void actionTracked(Player player);

    public void playerAttackTracked(Player player);

    public void monsterAttackTracked(Player player);

    public void playerAttackedByPlayerTracked(Player player);

    /**
     * Implement this if you want it.  Decided to only track things directly related to players.
     * @param player
     */
    //public void playerAttackedByMonsterTracked(Player player);

    public void chatTracked(Player player);

    public void placeTracked(Player player);

    public void breakTracked(Player player);

    public void shutdown(Player player);

}
