package org.getchunky.chunkyciv.listener;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.event.object.player.ChunkyPlayerChunkClaimEvent;
import org.getchunky.chunky.event.object.player.ChunkyPlayerChunkUnclaimEvent;
import org.getchunky.chunky.listeners.ChunkyPlayerEvents;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
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
        ChunkyCitizen citizen = CivManager.getCitizen(event.getChunkyPlayer());
        if (nation.isClosedBorders() && (!citizen.hasNation() || !citizen.getNation().equals(nation))) {
            org.getchunky.chunky.locale.Language.sendMessage(event.getChunkyPlayer(), "This nation has closed borders!  You may not claim chunks here!");
            event.setCancelled(true);
            return;
        }

        if (citizen.hasNation()) {
            if (!citizen.getNation().equals(nation)) {
                org.getchunky.chunky.locale.Language.sendMessage(event.getChunkyPlayer(), "You may not claim chunks in a nation that is not your nation!");
                event.setCancelled(true);
                return;
            }
        }

        if (!citizen.hasNation()) {
            citizen.setNation(nation).save();
            nation.addMember(event.getChunkyPlayer());
            nation.sendMessage("Claiming a chunk has added " + event.getChunkyPlayer().getName() + " to your nation.");
        }
    }

    public void onPlayerChunkUnclaim(ChunkyPlayerChunkUnclaimEvent event) {
        ChunkyCivChunk civChunk = CivManager.getCivChunk(event.getChunkyChunk());

        if (!civChunk.hasNation()) return;

        ChunkyNation nation = civChunk.getNation();
        ChunkyCitizen citizen = CivManager.getCitizen(event.getChunkyPlayer());

        if (event.getChunkyChunk().isOwnedBy(event.getChunkyPlayer())) {
            if (!CivManager.checkNationEligibility(citizen)) {
                citizen.setNation(null).save();
                nation.sendMessage("Unclaiming a chunk has removed " + event.getChunkyPlayer().getName() + " from your nation.");
            }
        }
    }
}
