package org.getchunky.chunkyciv.object;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyGroup;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.config.Config;
import org.getchunky.chunkyciv.util.Logging;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author dumptruckman
 */
public class ChunkyNation extends ChunkyGroup {

    private static String HOME_CHUNK = "home chunk";
    private static String CLAIMED_CHUNKS = "claimed chunks";
    private static String BONUS_CHUNKS = "bonus chunk claims";
    private static String OPEN_BORDERS = "open borders";
    private static String ACTION_TOTAL = "total action count";
    private static String PLAYER_ATTACK_TOTAL = "total player attack count";
    private static String MONSTER_ATTACK_TOTAL = "total monster attack count";
    private static String CHAT_TOTAL = "total chat count";
    private static String PLACE_TOTAL = "total place count";
    private static String BREAK_TOTAL = "total break count";

    private Integer claimMultiple;

    public ChunkyNation() {
        this.claimMultiple = Config.NATION_CHUNK_LIMIT.getInt() / org.getchunky.chunky.config.Config.getPlayerChunkLimitDefault();
    }

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
        civChunk.setNation(this).save();
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

    public JSONArray getChunks() {
        JSONArray chunks = getData().optJSONArray(CLAIMED_CHUNKS);
        if (chunks == null) {
            chunks = new JSONArray();
        }
        return chunks;
    }

    public Integer getClaimLimit() {
        return Config.NATION_CHUNK_LIMIT.getInt()
                //* org.getchunky.chunky.config.Config.getPlayerChunkLimitDefault()
                * this.getMembers().get(ChunkyPlayer.class.getName()).size()
                + getBonusChunks() + (int)(Math.sqrt(this.getTotalActionCount())*.7);
    }

    public Integer getBonusChunks() {
        return this.getData().optInt(BONUS_CHUNKS);
    }

    public void setBonusChunks(Integer limit) {
        getData().put(BONUS_CHUNKS, limit);
        save();
    }

    public Boolean isClosedBorders() {
        return getData().optBoolean(OPEN_BORDERS);
    }

    public void setOpenBorders(Boolean status) {
        getData().put(OPEN_BORDERS, status);
    }

    public void sendMessage(String message, Object...args) {
        for (ChunkyObject player : this.getMembers().get(ChunkyPlayer.class.getName())) {
            org.getchunky.chunky.locale.Language.sendMessage((ChunkyPlayer)player, message, args);
        }
    }

    public JSONObject getTrackingData() {
        JSONObject data = this.getData().optJSONObject("Action Tracking");
        if (data == null) {
            data = new JSONObject();
            this.getData().put("Action Tracking", data);
        }
        return data;
    }

    public Long getTotalActionCount() {
        return this.getTrackingData().optLong(ACTION_TOTAL);
    }

    public void incActionCount() {
        this.getTrackingData().put(ACTION_TOTAL, getTotalActionCount() + 1);
    }

    public Long getTotalPlayerAttackCount() {
        return this.getTrackingData().optLong(PLAYER_ATTACK_TOTAL);
    }

    public void incPlayerAttackCount() {
        this.getTrackingData().put(PLAYER_ATTACK_TOTAL, getTotalPlayerAttackCount() + 1);
        save();
    }

    public Long getTotalMonsterAttackCount() {
        return this.getTrackingData().optLong(MONSTER_ATTACK_TOTAL);
    }

    public void incMonsterAttackCount() {
        this.getTrackingData().put(MONSTER_ATTACK_TOTAL, getTotalMonsterAttackCount() + 1);
        save();
    }

    public Long getTotalChatCount() {
        return this.getTrackingData().optLong(CHAT_TOTAL);
    }

    public void incChatCount() {
        this.getTrackingData().put(CHAT_TOTAL, getTotalChatCount() + 1);
        save();
    }

    public Long getTotalPlaceCount() {
        return this.getTrackingData().optLong(PLACE_TOTAL);
    }

    public void incPlaceCount() {
        this.getTrackingData().put(PLACE_TOTAL, getTotalPlaceCount() + 1);
        save();
    }

    public Long getTotalBreakCount() {
        return this.getTrackingData().optLong(BREAK_TOTAL);
    }

    public void incBreakCount() {
        this.getTrackingData().put(BREAK_TOTAL, getTotalBreakCount() + 1);
        save();
    }
}
