package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunkyciv.util.Logging;

/**
 * @author dumptruckman
 */
public class ChunkyCivChunk {

    private static String CHUNK_CIV = "civ: civilization";

    private ChunkyChunk chunkyChunk;

    public ChunkyCivChunk(ChunkyChunk chunk) {
        this.chunkyChunk = chunk;
    }

    public ChunkyChunk getChunkyChunk() {
        return chunkyChunk;
    }

    public ChunkyCivilization getCivilization() {
        String civId = getChunkyChunk().getData().optString(CHUNK_CIV);
        if (civId == null) return null;
        ChunkyCivilization civ = (ChunkyCivilization)ChunkyManager.getObject(ChunkyCivilization.class.getName(), civId);
        if (civ == null) {
            Logging.warning("Chunk belongs to non-existent civilization!");
            getChunkyChunk().getData().remove(CHUNK_CIV);
        }
        return civ;
    }

    public ChunkyCivChunk setCivilization(ChunkyCivilization civ) {
        if (civ == null) {
            getChunkyChunk().getData().remove(CHUNK_CIV);
            return this;
        }
        getChunkyChunk().getData().put(CHUNK_CIV, civ.getId());
        return this;
    }

    public Boolean hasCivilization() {
        return getChunkyChunk().getData().optString(CHUNK_CIV) != null;
    }
}
