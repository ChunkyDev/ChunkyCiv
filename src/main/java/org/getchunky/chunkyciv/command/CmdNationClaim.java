package org.getchunky.chunkyciv.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyObject;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.permission.bukkit.Permissions;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivChunk;
import org.getchunky.chunkyciv.object.ChunkyNation;
import org.getchunky.chunkyciv.permission.Perm;
import org.getchunky.chunkyciv.util.Logging;

/**
 * @author dumptruckman
 */
public class CmdNationClaim implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player)sender));

        if (!citizen.hasNation()) {
            Language.NO_NAT.bad(sender);
            return;
        }
        if (!citizen.canCivNationClaim()) {
            Language.NAT_NO_PERM_CLAIM.bad(sender);
            return;
        }

        ChunkyCivChunk civChunk = CivManager.getCivChunk(citizen.getChunkyPlayer().getCurrentChunk());
        if (civChunk.hasNation()) {
            Logging.debug(civChunk.getNation() + " vs " + citizen.getNation());
            Language.CHUNK_HAS_NAT.bad(sender, civChunk.getNation().getName());
            return;
        }

        ChunkyObject chunkOwner = civChunk.getChunkyChunk().getOwner();
        if (chunkOwner != null && chunkOwner instanceof ChunkyPlayer) {
            ChunkyCitizen ownerCitizen = CivManager.getCitizen((ChunkyPlayer)chunkOwner);
            if (ownerCitizen.hasNation() && !ownerCitizen.getNation().equals(citizen.getNation())) {
                Language.BELONGS_TO_OTHERS_CIT.bad(sender, ownerCitizen.getNation().getName());
                return;
            }
        }

        ChunkyNation civ = citizen.getNation();

        boolean isAdjacent = false;
        for (ChunkyChunk cChunk : civChunk.getChunkyChunk().getDirectlyAdjacentChunks()) {
            ChunkyCivChunk adjCivChunk = CivManager.getCivChunk(cChunk);
            if (adjCivChunk.hasNation()) {
                adjCivChunk.getNation()
            }
        }

        if (!Permissions.PLAYER_NO_CHUNK_LIMIT.hasPerm((Player)sender) && civ.getChunks().length() >= civ.getClaimLimit()) {
            Language.NAT_CLAIM_LIMIT.bad(sender, civ.getChunks().length(), civ.getClaimLimit());
            return;
        }

        civ.claimChunk(civChunk).save();

        Language.NAT_CLAIM_CHUNK.good(sender, civ.getName(), civChunk.getChunkyChunk().getCoord().toString());
    }
}
