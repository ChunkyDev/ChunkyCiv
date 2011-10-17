package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunkyciv.util.Logging;
import org.json.JSONObject;

/**
 * @author dumptruckman
 */
public class ChunkyCivChunk {

    private static String CHUNK_NATION = "nation";

    private ChunkyChunk chunkyChunk;

    public ChunkyCivChunk(ChunkyChunk chunk) {
        this.chunkyChunk = chunk;
    }

    public ChunkyChunk getChunkyChunk() {
        return chunkyChunk;
    }

    public JSONObject getData() {
        JSONObject data = this.getChunkyChunk().getData().optJSONObject("ChunkyCiv");
        if (data == null) {
            data = new JSONObject();
            this.getChunkyChunk().getData().put("ChunkyCiv", data);
        }
        return data;
    }

    public ChunkyNation getNation() {
        String civId = this.getData().optString(CHUNK_NATION);
        if (civId.isEmpty()) return null;
        ChunkyNation civ = (ChunkyNation)ChunkyManager.getObject(ChunkyNation.class.getName(), civId);
        if (civ == null) {
            Logging.warning("Chunk belongs to non-existent nation!");
            this.getData().remove(CHUNK_NATION);
        }
        return civ;
    }

    public ChunkyCivChunk setNation(ChunkyNation civ) {
        if (civ == null) {
            this.getData().remove(CHUNK_NATION);
            return this;
        }
        this.getData().put(CHUNK_NATION, civ.getId());
        return this;
    }

    public Boolean hasNation() {
        return !this.getData().optString(CHUNK_NATION).isEmpty();
    }

    public boolean save() {
        return this.getChunkyChunk().save();
    }
}
