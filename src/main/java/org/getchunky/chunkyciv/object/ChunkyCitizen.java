package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.persistance.ChunkyPersistable;

/**
 * @author dumptruckman
 */
public class ChunkyCitizen {

    private ChunkyPlayer chunkyPlayer;

    public ChunkyCitizen(ChunkyPlayer chunkyPlayer) {
        this.chunkyPlayer = chunkyPlayer;
    }

    public ChunkyPlayer getChunkyPlayer() {
        return chunkyPlayer;
    }
}
