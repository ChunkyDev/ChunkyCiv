package org.getchunky.chunkyciv.listener;

import org.getchunky.chunky.event.object.player.ChunkyPlayerChunkChangeEvent;
import org.getchunky.chunky.event.object.player.ChunkyPlayerListener;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyNation;

/**
 * @author dumptruckman, SwearWord
 */
public class ChunkyPlayerEvents extends ChunkyPlayerListener {

    public void onPlayerChunkChange(ChunkyPlayerChunkChangeEvent event) {
        ChunkyCivChunk civToChunk = CivManager.getCivChunk(event.getToChunk());
        ChunkyCivChunk civFromChunk = CivManager.getCivChunk(event.getFromChunk());
        ChunkyNation toChunkNation = civToChunk.getNation();
        if ((civFromChunk.hasNation() && !civFromChunk.getNation().equals(toChunkNation))
                || (!civFromChunk.hasNation() && civToChunk.hasNation())) {
            String nationName;
            if (toChunkNation != null)
                nationName = " [Nation: " + toChunkNation.getName() + "]";
            else {
                nationName = " [No Nation]";
            }
            if (!event.getMessage().contains(event.getToChunkDisplayName())) {
                event.setMessage(event.getToChunkDisplayName() + event.getMessage());
            }
            event.setMessage(event.getMessage() + nationName);
        }
    }
}
