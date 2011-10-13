package org.getchunky.chunkyciv.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyCivilization;

/**
 * @author dumptruckman
 */
public class CmdCivUnclaim implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player) sender));

        if (!citizen.hasCivilization()) {
            Language.NO_CIV.bad(sender);
            return;
        }
        if (!citizen.canCivClaim()) {
            Language.CIV_NO_PERM_UNCLAIM.bad(sender);
            return;
        }

        ChunkyCivChunk civChunk = CivManager.getCivChunk(citizen.getChunkyPlayer().getCurrentChunk());
        if (!civChunk.hasCivilization()) {
            Language.CHUNK_NO_CIV.bad(sender);
            return;
        }

        if (!civChunk.getCivilization().equals(citizen.getCivilization())) {
            Language.CHUNK_CIV.bad(sender, civChunk.getCivilization().getName());
            return;
        }

        ChunkyCivilization civ = citizen.getCivilization();
        civ.unclaimChunk(civChunk);
        Language.CIV_UNCLAIM_CHUNK.good(sender, civ.getName(), civChunk.getChunkyChunk().getCoord().toString());
    }
}
