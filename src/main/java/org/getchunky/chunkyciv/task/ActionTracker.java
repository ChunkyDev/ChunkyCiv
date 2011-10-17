package org.getchunky.chunkyciv.task;

import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.object.ChunkyCitizen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author dumptruckman
 */
public class ActionTracker implements Runnable {

    private final static HashSet<Player> playerPlayerAttack = new HashSet<Player>();
    private final static HashSet<Player> playerMonsterAttack = new HashSet<Player>();
    private final static HashSet<Player> playerPlace = new HashSet<Player>();
    private final static HashSet<Player> playerBreak = new HashSet<Player>();
    private final static HashSet<Player> playerChat = new HashSet<Player>();
    private final static HashSet<Player> playerAction = new HashSet<Player>();

    public void shutDown() {
        for (Player player : playerAction) {
            ChunkyManager.getChunkyPlayer(player).save();
        }
    }

    public void run() {
        playerPlayerAttack.clear();
        playerMonsterAttack.clear();
        playerPlace.clear();
        playerBreak.clear();
        playerChat.clear();
        playerAction.clear();
    }

    private static void trackAction(Player player) {
        try {
            Boolean wasTracked = playerAction.contains(player);
            playerAction.add(player);
            
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incActionCount();
            }
        } catch (Exception ignore) {}
    }

    public static void trackPlayerAttack(Player player) {
        try {
            Boolean wasTracked = playerPlayerAttack.contains(player);
            playerPlayerAttack.add(player);

            trackAction(player);
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incPlayerAttackCount();
            }
        } catch (Exception ignore) {}
    }

    public static void trackMonsterAttack(Player player) {
        try {
            Boolean wasTracked = playerMonsterAttack.contains(player);
            playerMonsterAttack.add(player);

            trackAction(player);
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incMonsterAttackCount();
            }
        } catch (Exception ignore) {}
    }

    public static void trackChat(Player player) {
        try {
            Boolean wasTracked = playerChat.contains(player);
            playerChat.add(player);

            trackAction(player);
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incChatCount();
            }
        } catch (Exception ignore) {}
    }

    public static void trackPlace(Player player) {
        try {
            Boolean wasTracked = playerPlace.contains(player);
            playerPlace.add(player);

            trackAction(player);
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incPlaceCount();
            }
        } catch (Exception ignore) {}
    }

    public static void trackBreak(Player player) {
        try {
            Boolean wasTracked = playerBreak.contains(player);
            playerBreak.add(player);

            trackAction(player);
            if (!wasTracked) {
                ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer(player));
                citizen.incBreakCount();
            }
        } catch (Exception ignore) {}
    }

}
