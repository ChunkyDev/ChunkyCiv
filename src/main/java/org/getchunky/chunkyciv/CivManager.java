package org.getchunky.chunkyciv;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.permission.PermissionFlag;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyNation;
import org.getchunky.chunkyciv.util.Logging;

import java.util.HashMap;

/**
 * @author dumptruckman
 */
public class CivManager {

    public static PermissionFlag CIV_CLAIM = new PermissionFlag("Civ Claim", "cc");
    public static PermissionFlag CIV_UNCLAIM = new PermissionFlag("Civ Unclaim", "cu");

    private static HashMap<ChunkyPlayer, ChunkyCitizen> citizensMap = new HashMap<ChunkyPlayer, ChunkyCitizen>();
    private static HashMap<String, ChunkyNation> nationsMap = new HashMap<String, ChunkyNation>();
    private static HashMap<ChunkyChunk, ChunkyCivChunk> chunksMap = new HashMap<ChunkyChunk, ChunkyCivChunk>();

    public static ChunkyCitizen getCitizen(ChunkyPlayer player) {
        ChunkyCitizen cit = citizensMap.get(player);
        if (cit == null) {
            cit = new ChunkyCitizen(player);
            citizensMap.put(player, cit);
        }
        return cit;
    }

    public static ChunkyNation getNation(String name) {
        ChunkyNation nation = nationsMap.get(name);
        if (nation == null) {
            for (ChunkyObject object : ChunkyManager.getObjectsOfType(ChunkyNation.class.getName()).values()) {
                if (object.getName().equalsIgnoreCase(name)) {
                    nation = (ChunkyNation)object;
                    nationsMap.put(nation.getName(), nation);
                    break;
                }
            }
        }
        Logging.debug("Got nation: " + nation);
        return nation;
    }

    public static ChunkyNation createNation(String name) {
        ChunkyNation nation = getNation(name);
        if (nation != null)
            return null;
        nation = new ChunkyNation();
        nation.setId(ChunkyManager.getUniqueId());
        nation.setName(name);
        Logging.debug("Created a new nation: " + name);
        return nation;
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
