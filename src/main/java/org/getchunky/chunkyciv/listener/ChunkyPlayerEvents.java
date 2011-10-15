package org.getchunky.chunkyciv.listener;

import org.getchunky.chunky.event.object.player.ChunkyPlayerChunkChangeEvent;
import org.getchunky.chunky.event.object.player.ChunkyPlayerListener;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;

/**
 * @author dumptruckman, SwearWord
 */
public class ChunkyPlayerEvents extends ChunkyPlayerListener {

    public void onPlayerChunkChange(ChunkyPlayerChunkChangeEvent event) {
        ChunkyCivChunk civChunk = CivManager.getCivChunk(event.getToChunk());
        if (civChunk.hasNation()) {
            event.setMessage(event.getMessage() + "Nation: " + civChunk.getNation().getName());
        }
    }
}
