package org.getchunky.chunkyciv;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.permission.PermissionFlag;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyCivilization;
import org.getchunky.chunkyciv.util.Logging;

import java.util.HashMap;

/**
 * @author dumptruckman
 */
public class CivManager {

    public static PermissionFlag CIV_CLAIM = new PermissionFlag("Civ Claim", "cc");
    public static PermissionFlag CIV_UNCLAIM = new PermissionFlag("Civ Unclaim", "cu");

    private static HashMap<ChunkyPlayer, ChunkyCitizen> citizensMap = new HashMap<ChunkyPlayer, ChunkyCitizen>();
    private static HashMap<String, ChunkyCivilization> civilizationsMap = new HashMap<String, ChunkyCivilization>();
    private static HashMap<ChunkyChunk, ChunkyCivChunk> chunksMap = new HashMap<ChunkyChunk, ChunkyCivChunk>();

    public static ChunkyCitizen getCitizen(ChunkyPlayer player) {
        ChunkyCitizen cit = citizensMap.get(player);
        if (cit == null) {
            cit = new ChunkyCitizen(player);
            citizensMap.put(player, cit);
        }
        return cit;
    }

    public static ChunkyCivilization getCivilization(String name) {
        ChunkyCivilization civilization = civilizationsMap.get(name);
        if (civilization == null) {
            for (ChunkyObject object : ChunkyManager.getObjectsOfType(ChunkyCivilization.class.getName()).values()) {
                if (object.getName().equalsIgnoreCase(name)) {
                    civilization = (ChunkyCivilization)object;
                    civilizationsMap.put(civilization.getName(), civilization);
                    break;
                }
            }
        }
        Logging.debug("Got civilization: " + civilization);
        return civilization;
    }

    public static ChunkyCivilization createCivilization(String name) {
        ChunkyCivilization civilization = getCivilization(name);
        if (civilization != null)
            return null;
        civilization = new ChunkyCivilization();
        civilization.setId(ChunkyManager.getUniqueId());
        civilization.setName(name);
        Logging.debug("Created a new civilization: " + name);
        return civilization;
    }
    
    public static ChunkyCivChunk getCivChunk(ChunkyChunk chunk) {
        ChunkyCivChunk civChunk = chunksMap.get(chunk);
        if (civChunk == null) {
            civChunk = new ChunkyCivChunk(chunk);
            chunksMap.put(chunk, civChunk);
        }
        return civChunk;
    }
}
