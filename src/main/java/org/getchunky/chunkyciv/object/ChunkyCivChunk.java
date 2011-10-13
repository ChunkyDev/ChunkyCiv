package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunkyciv.util.Logging;

/**
 * @author dumptruckman
 */
public class ChunkyCivChunk {

    private static String CHUNK_NATION = "civ: nation";

    private ChunkyChunk chunkyChunk;

    public ChunkyCivChunk(ChunkyChunk chunk) {
        this.chunkyChunk = chunk;
    }

    public ChunkyChunk getChunkyChunk() {
        return chunkyChunk;
    }

    public ChunkyNation getNation() {
        String civId = getChunkyChunk().getData().optString(CHUNK_NATION);
        if (civId == null) return null;
        ChunkyNation civ = (ChunkyNation)ChunkyManager.getObject(ChunkyNation.class.getName(), civId);
        if (civ == null) {
            Logging.warning("Chunk belongs to non-existent nation!");
            getChunkyChunk().getData().remove(CHUNK_NATION);
        }
        return civ;
    }

    public ChunkyCivChunk setNation(ChunkyNation civ) {
        if (civ == null) {
            getChunkyChunk().getData().remove(CHUNK_NATION);
            return this;
        }
        getChunkyChunk().getData().put(CHUNK_NATION, civ.getId());
        return this;
    }

    public Boolean hasNation() {
        return getChunkyChunk().getData().optString(CHUNK_NATION) != null;
    }
}
