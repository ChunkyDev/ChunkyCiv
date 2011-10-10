package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.json.JSONObject;

/**
 * @author dumptruckman
 */
public class ChunkyCitizen {

    private ChunkyPlayer chunkyPlayer;
    private ChunkyCivilization civilization = null;

    public ChunkyCitizen(ChunkyPlayer chunkyPlayer) {
        this.chunkyPlayer = chunkyPlayer;
    }

    public ChunkyPlayer getChunkyPlayer() {
        return this.chunkyPlayer;
    }

    public JSONObject getData() {
        return this.getChunkyPlayer().getData();
    }

    public Boolean hasCivilization() {
        return this.getData().optString("civilization") != null;
    }

    public ChunkyCivilization getCivilization() {
        if (this.hasCivilization()) {
            String nationId = this.getData().optString("civilization");
            if (this.civilization == null || !this.civilization.getId().equals(nationId))
                this.civilization = (ChunkyCivilization)ChunkyManager.getObject(ChunkyCivilization.class.getName(), nationId);
            return this.civilization;
        } else
            return null;
    }
}
