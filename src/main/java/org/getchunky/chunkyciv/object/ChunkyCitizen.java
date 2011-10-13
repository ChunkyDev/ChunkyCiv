package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.CivManager;
import org.json.JSONObject;

/**
 * @author dumptruckman
 */
public class ChunkyCitizen {

    private ChunkyPlayer chunkyPlayer;
    private ChunkyNation nation = null;

    public ChunkyCitizen(ChunkyPlayer chunkyPlayer) {
        this.chunkyPlayer = chunkyPlayer;
    }

    public ChunkyPlayer getChunkyPlayer() {
        return this.chunkyPlayer;
    }

    public JSONObject getData() {
        return this.getChunkyPlayer().getData();
    }

    public Boolean hasNation() {
        return this.getData().optString("nation") != null;
    }

    public ChunkyNation getNation() {
        if (this.hasNation()) {
            String nationId = this.getData().optString("nation");
            if (this.nation == null || !this.nation.getId().equals(nationId))
                this.nation = (ChunkyNation)ChunkyManager.getObject(ChunkyNation.class.getName(), nationId);
            return this.nation;
        } else
            return null;
    }

    public Boolean canCivClaim() {
        if (!this.hasNation()) return false;
        ChunkyNation civ = this.getNation();
        if (civ.isOwnedBy(this.getChunkyPlayer())) return true;
        if (this.getChunkyPlayer().hasPerm(civ, CivManager.CIV_CLAIM)) return true;
        return false;
    }
}
