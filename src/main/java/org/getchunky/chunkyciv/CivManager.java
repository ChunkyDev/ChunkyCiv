package org.getchunky.chunkyciv;

import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.object.ChunkyCitizen;

import java.util.HashMap;

/**
 * @author dumptruckman
 */
public class CivManager {

    private static HashMap<ChunkyPlayer, ChunkyCitizen> citizensMap = new HashMap<ChunkyPlayer, ChunkyCitizen>();

    public static ChunkyCitizen getCitizen(ChunkyPlayer player) {
        ChunkyCitizen cit = citizensMap.get(player);
        if (cit == null) cit = new ChunkyCitizen(player);
        return cit;
    }
}
