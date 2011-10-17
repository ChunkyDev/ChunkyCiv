package org.getchunky.chunkyciv.listener;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.event.object.player.ChunkyPlayerChunkClaimEvent;
import org.getchunky.chunky.listeners.ChunkyPlayerEvents;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyNation;

/**
 * @author dumptruckman, SwearWord
 */
public class ChunkClaimEvent extends ChunkyPlayerEvents {

    public void onPlayerChunkClaim(ChunkyPlayerChunkClaimEvent event) {
        ChunkyCivChunk civChunk = CivManager.getCivChunk(event.getChunkyChunk());

        if (!civChunk.hasNation()) return;

        ChunkyNation nation = civChunk.getNation();
        if (nation.isClosedBorders()) {
            ChunkyCitizen citizen = CivManager.getCitizen(event.getChunkyPlayer());
            if (!citizen.hasNation() || !citizen.getNation().equals(nation)) {
                org.getchunky.chunky.locale.Language.sendMessage(event.getChunkyPlayer(), "This nation has closed borders!  You may not claim chunks here!");
                event.setCancelled(true);
            }
        }
    }
}
