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
import org.getchunky.chunkyciv.util.PluginTools;

/**
 * @author dumptruckman
 */
public class CmdNationNew implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        if (args.length < 1) {
            Language.CMD_NAT_NEW_HELP.bad(sender);
            return;
        }

        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player)sender));

        if (citizen.hasNation()) {
            Language.HAS_NAT.bad(sender, citizen.getNation().getName());
            return;
        }

        ChunkyCivChunk civChunk = CivManager.getCivChunk(citizen.getChunkyPlayer().getCurrentChunk());
        ChunkyNation nation = civChunk.getNation();
        if (nation != null) {
            Language.NO_CREATE_ON_NAT.bad(sender);
            return;
        }

        ChunkyObject chunkOwner = civChunk.getChunkyChunk().getOwner();
        if (chunkOwner != null && chunkOwner instanceof ChunkyPlayer) {
            ChunkyCitizen ownerCitizen = CivManager.getCitizen((ChunkyPlayer)chunkOwner);
            if (ownerCitizen.hasNation()) {
                Language.BELONGS_TO_OTHERS_CIT.bad(sender, ownerCitizen.getNation().getName());
                return;
            }
        }

        String name = PluginTools.combineStringArray(args);
        nation = CivManager.createNation(name);
        if (nation == null) {
            Language.NAT_EXISTS.bad(sender, name);
            return;
        }

        nation.setOwner(citizen.getChunkyPlayer(), true, false);
        nation.claimChunk(civChunk).setHomeChunk(civChunk.getChunkyChunk()).save();
        nation.addMember(citizen.getChunkyPlayer());
        citizen.setNation(nation).save();
    }
}
