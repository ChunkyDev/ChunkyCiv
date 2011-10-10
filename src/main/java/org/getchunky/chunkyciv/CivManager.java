package org.getchunky.chunkyciv;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivilization;

import java.util.HashMap;

/**
 * @author dumptruckman
 */
public class CivManager {

    private static HashMap<ChunkyPlayer, ChunkyCitizen> citizensMap = new HashMap<ChunkyPlayer, ChunkyCitizen>();
    private static HashMap<String, ChunkyCivilization> nationsMap = new HashMap<String, ChunkyCivilization>();

    public static ChunkyCitizen getCitizen(ChunkyPlayer player) {
        ChunkyCitizen cit = citizensMap.get(player);
        if (cit == null) {
            cit = new ChunkyCitizen(player);
            citizensMap.put(player, cit);
        }
        return cit;
    }

    public static ChunkyCivilization getNation(String name) {
        ChunkyCivilization civilization = nationsMap.get(name);
        if (civilization == null) {
            for (ChunkyObject object : ChunkyManager.getObjectsOfType(ChunkyCivilization.class.getName()).values()) {
                if (object.getName().equalsIgnoreCase(name)) {
                    civilization = (ChunkyCivilization)object;
                    nationsMap.put(civilization.getName(), civilization);
                    break;
                }
            }
        }
        return civilization;
    }

    public static ChunkyCivilization createNation(String name) {
        ChunkyCivilization civilization = getNation(name);
        if (civilization != null)
            return null;
        civilization = new ChunkyCivilization();
        civilization.setId(ChunkyManager.getUniqueId());
        civilization.setName(name);
        return civilization;
    }
}
