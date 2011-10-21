package org.getchunky.chunkyciv.actiontracker;

import org.bukkit.entity.Player;
import org.getchunky.actiontracker.PersistenceInterface;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.object.ChunkyCitizen;

/**
 * @author dumptruckman
 */
public class ChunkyActionPersistence implements PersistenceInterface {

    public void actionTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incActionCount();
    }

    public void playerAttackTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incPlayerAttackCount();
    }

    public void monsterAttackTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incMonsterAttackCount();
    }

    public void playerAttackedByPlayerTracked(Player player) {
        
    }

    public void chatTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incChatCount();
    }

    public void placeTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incPlaceCount();
    }

    public void breakTracked(Player player) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
        citizen.incBreakCount();
    }

    public void shutdown(Player player) {
        ChunkyManager.getChunkyPlayer(player).save();
    }
}
