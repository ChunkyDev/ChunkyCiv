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
public class ChunkyNation extends ChunkyGroup {

    private static String HOME_CHUNK = "home chunk";
    private static String CLAIMED_CHUNKS = "claimed chunks";

    public ChunkyCivChunk getHomeChunk() {
        String homeChunk = getData().optString(HOME_CHUNK);
        if (homeChunk == null) {
            Logging.severe("Nation '" + getName() + "' has no home chunk!");
            return null;
        }
        return CivManager.getCivChunk((ChunkyChunk) ChunkyManager.getObject(ChunkyChunk.class.getName(), homeChunk));
    }

    public ChunkyNation setHomeChunk(ChunkyChunk chunk) {
        getData().put(HOME_CHUNK, chunk.getId());
        return this;
    }

    public ChunkyNation claimChunk(ChunkyCivChunk civChunk) {
        JSONArray chunks = getData().optJSONArray(CLAIMED_CHUNKS);
        if (chunks == null) {
            chunks = new JSONArray();
        }
        chunks.put(civChunk.getChunkyChunk().getId());
        getData().put(CLAIMED_CHUNKS, chunks);
        civChunk.setNation(this);
        return this;
    }

    public ChunkyNation unclaimChunk(ChunkyCivChunk civChunk) {
        JSONArray chunks = getData().optJSONArray(CLAIMED_CHUNKS);
        if (chunks == null) {
            chunks = new JSONArray();
        }
        for (int i = 0; i < chunks.length(); i++) {
            if (chunks.getString(i).equals(civChunk.getChunkyChunk().getId())) {
                chunks.remove(i);
                break;
            }
        }
        getData().put(CLAIMED_CHUNKS, chunks);
        civChunk.setNation(null);
        return this;
    }
}
