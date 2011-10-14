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

    private static String NATION = "civ: nation";
    private static String ACTION_TOTAL = "civ: total action count";
    private static String PLAYER_ATTACK_TOTAL = "civ: total player attack count";
    private static String MONSTER_ATTACK_TOTAL = "civ: total monster attack count";
    private static String CHAT_TOTAL = "civ: total chat count";
    private static String PLACE_TOTAL = "civ: total place count";
    private static String BREAK_TOTAL = "civ: total break count";

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
        return this.getData().optString(NATION) != null;
    }

    public ChunkyNation getNation() {
        if (this.hasNation()) {
            String nationId = this.getData().optString(NATION);
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

    public Long getTotalActionCount() {
        return getData().optLong(ACTION_TOTAL);
    }

    public void incActionCount() {
        getData().put(ACTION_TOTAL, getTotalActionCount());
    }

    public Long getTotalPlayerAttackCount() {
        return getData().optLong(PLAYER_ATTACK_TOTAL);
    }

    public void incPlayerAttackCount() {
        getData().put(PLAYER_ATTACK_TOTAL, getTotalPlayerAttackCount());
    }

    public Long getTotalMonsterAttackCount() {
        return getData().optLong(MONSTER_ATTACK_TOTAL);
    }

    public void incMonsterAttackCount() {
        getData().put(MONSTER_ATTACK_TOTAL, getTotalMonsterAttackCount());
    }

    public Long getTotalChatCount() {
        return getData().optLong(CHAT_TOTAL);
    }

    public void incChatCount() {
        getData().put(CHAT_TOTAL, getTotalChatCount());
    }

    public Long getTotalPlaceCount() {
        return getData().optLong(PLACE_TOTAL);
    }

    public void incPlaceCount() {
        getData().put(PLACE_TOTAL, getTotalPlaceCount());
    }

    public Long getTotalBreakCount() {
        return getData().optLong(BREAK_TOTAL);
    }

    public void incBreakCount() {
        getData().put(BREAK_TOTAL, getTotalBreakCount());
    }
}
