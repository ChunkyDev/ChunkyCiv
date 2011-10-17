package org.getchunky.chunkyciv.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyNation;

/**
 * @author dumptruckman
 */
public class CmdNationUnclaim implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player) sender));

        if (!citizen.hasNation()) {
            Language.NO_NAT.bad(sender);
            return;
        }
        if (!citizen.canCivNationClaim()) {
            Language.NAT_NO_PERM_UNCLAIM.bad(sender);
            return;
        }

        ChunkyCivChunk civChunk = CivManager.getCivChunk(citizen.getChunkyPlayer().getCurrentChunk());
        if (!civChunk.hasNation()) {
            Language.CHUNK_NO_NAT.bad(sender);
            return;
        }

        if (!civChunk.getNation().equals(citizen.getNation())) {
            Language.CHUNK_NAT.bad(sender, civChunk.getNation().getName());
            return;
        }

        ChunkyNation civ = citizen.getNation();
        if (civ.getHomeChunk().equals(civChunk)) {
            sender.sendMessage("You may not unclaim your nation's home chunk!");
            return;
        }

        civ.unclaimChunk(civChunk);
        Language.NAT_UNCLAIM_CHUNK.good(sender, civ.getName(), civChunk.getChunkyChunk().getCoord().toString());

        ChunkyObject chunkOwner = civChunk.getChunkyChunk().getOwner();
        if (chunkOwner != null && chunkOwner instanceof ChunkyPlayer) {
            ChunkyCitizen ownerCitizen = CivManager.getCitizen((ChunkyPlayer)chunkOwner);
            if (!CivManager.checkNationEligibility(ownerCitizen)) {
                ownerCitizen.setNation(null).save();
                civ.sendMessage("Unclaiming a chunk has removed " + chunkOwner.getName() + " from your nation.");
            }
        }
    }
}
