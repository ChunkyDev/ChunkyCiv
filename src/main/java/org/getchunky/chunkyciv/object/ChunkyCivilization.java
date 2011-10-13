package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyGroup;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.util.Logging;
import org.json.JSONArray;

/**
 * @author dumptruckman
 */
public class ChunkyCivilization extends ChunkyGroup {

    private static String HOME_CHUNK = "home chunk";
    private static String CLAIMED_CHUNKS = "claimed chunks";

    public ChunkyCivChunk getHomeChunk() {
        String homeChunk = getData().optString(HOME_CHUNK);
        if (homeChunk == null) {
            Logging.severe("Civilization '" + getName() + "' has no home chunk!");
            return null;
        }
        return CivManager.getCivChunk((ChunkyChunk) ChunkyManager.getObject(ChunkyChunk.class.getName(), homeChunk));
    }

    public ChunkyCivilization setHomeChunk(ChunkyChunk chunk) {
        getData().put(HOME_CHUNK, chunk.getId());
        return this;
    }

    public ChunkyCivilization claimChunk(ChunkyCivChunk civChunk) {
        JSONArray chunks = getData().optJSONArray(CLAIMED_CHUNKS);
        if (chunks == null) {
            chunks = new JSONArray();
        }
        chunks.put(civChunk.getChunkyChunk().getId());
        getData().put(CLAIMED_CHUNKS, chunks);
        civChunk.setCivilization(this);
        return this;
    }
}