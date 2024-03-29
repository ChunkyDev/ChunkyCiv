package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.util.Logging;
import org.json.JSONObject;

/**
 * @author dumptruckman
 */
public class ChunkyCitizen {

    private ChunkyPlayer chunkyPlayer;
    private ChunkyNation nation = null;

    private static String NATION = "nation";
    private static String ACTION_TOTAL = "total action count";
    private static String PLAYER_ATTACK_TOTAL = "total player attack count";
    private static String MONSTER_ATTACK_TOTAL = "total monster attack count";
    private static String CHAT_TOTAL = "total chat count";
    private static String PLACE_TOTAL = "total place count";
    private static String BREAK_TOTAL = "total break count";

    public ChunkyCitizen(ChunkyPlayer chunkyPlayer) {
        this.chunkyPlayer = chunkyPlayer;
    }

    public ChunkyPlayer getChunkyPlayer() {
        return this.chunkyPlayer;
    }

    public JSONObject getData() {
        JSONObject data = this.getChunkyPlayer().getData().optJSONObject("ChunkyCiv");
        if (data == null) {
            data = new JSONObject();
            this.getChunkyPlayer().getData().put("ChunkyCiv", data);
        }
        return data;
    }

    public JSONObject getTrackingData() {
        JSONObject data = this.getData().optJSONObject("Action Tracking");
        if (data == null) {
            data = new JSONObject();
            this.getData().put("Action Tracking", data);
        }
        return data;
    }

    public Boolean hasNation() {
        return !this.getData().optString(NATION).isEmpty();
    }

    public ChunkyNation getNation() {
        if (this.hasNation()) {
            String nationId = this.getData().optString(NATION);
            if (this.nation == null || !this.nation.getId().equals(nationId)) {
                this.nation = (ChunkyNation)ChunkyManager.getObject(ChunkyNation.class.getName(), nationId);
            }
            return this.nation;
        } else
            return null;
    }

    public ChunkyCitizen setNation(ChunkyNation nation) {
        if (nation == null)
            getData().remove(NATION);
        else
            getData().put(NATION, nation.getId());
        return this;
    }

    public Boolean canCivNationClaim() {
        if (!this.hasNation()) return false;
        ChunkyNation civ = this.getNation();
        if (civ.isOwnedBy(this.getChunkyPlayer())) return true;
        if (this.getChunkyPlayer().hasPerm(civ, CivManager.CIV_CLAIM)) return true;
        return false;
    }

    public Long getTotalActionCount() {
        return this.getTrackingData().optLong(ACTION_TOTAL);
    }

    public void incActionCount() {
        this.getTrackingData().put(ACTION_TOTAL, getTotalActionCount() + 1);
        if (this.hasNation())
            this.getNation().incActionCount();
    }

    public Long getTotalPlayerAttackCount() {
        return this.getTrackingData().optLong(PLAYER_ATTACK_TOTAL);
    }

    public void incPlayerAttackCount() {
        this.getTrackingData().put(PLAYER_ATTACK_TOTAL, getTotalPlayerAttackCount() + 1);
        save();
        if (this.hasNation())
            this.getNation().incPlayerAttackCount();
    }

    public Long getTotalMonsterAttackCount() {
        return this.getTrackingData().optLong(MONSTER_ATTACK_TOTAL);
    }

    public void incMonsterAttackCount() {
        this.getTrackingData().put(MONSTER_ATTACK_TOTAL, getTotalMonsterAttackCount() + 1);
        save();
        if (this.hasNation())
            this.getNation().incMonsterAttackCount();
    }

    public Long getTotalChatCount() {
        return this.getTrackingData().optLong(CHAT_TOTAL);
    }

    public void incChatCount() {
        this.getTrackingData().put(CHAT_TOTAL, getTotalChatCount() + 1);
        save();
        if (this.hasNation())
            this.getNation().incChatCount();
    }

    public Long getTotalPlaceCount() {
        return this.getTrackingData().optLong(PLACE_TOTAL);
    }

    public void incPlaceCount() {
        this.getTrackingData().put(PLACE_TOTAL, getTotalPlaceCount() + 1);
        save();
        if (this.hasNation())
            this.getNation().incPlaceCount();
    }

    public Long getTotalBreakCount() {
        return this.getTrackingData().optLong(BREAK_TOTAL);
    }

    public void incBreakCount() {
        this.getTrackingData().put(BREAK_TOTAL, getTotalBreakCount() + 1);
        save();
        if (this.hasNation())
            this.getNation().incBreakCount();
    }

    public boolean save() {
        return this.getChunkyPlayer().save();
    }
}
