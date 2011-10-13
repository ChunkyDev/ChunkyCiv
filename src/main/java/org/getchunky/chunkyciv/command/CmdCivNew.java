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
import org.getchunky.chunkyciv.permission.Perm;
import org.getchunky.chunkyciv.util.PluginTools;

/**
 * @author dumptruckman
 */
public class CmdCivNew implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        if (args.length < 1) {
            Language.CMD_CIV_NEW_HELP.bad(sender);
            return;
        }

        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player)sender));

        if (citizen.hasCivilization()) {
            Language.HAS_CIV.bad(sender, citizen.getCivilization().getName());
            return;
        }

        ChunkyCivChunk civChunk = CivManager.getCivChunk(citizen.getChunkyPlayer().getCurrentChunk());
        ChunkyCivilization civilization = civChunk.getCivilization();
        if (civilization != null) {
            Language.NO_CREATE_ON_CIV.bad(sender);
            return;
        }

        String name = PluginTools.combineStringArray(args);
        civilization = CivManager.createCivilization(name);
        if (civilization == null) {
            Language.CIV_EXISTS.bad(sender, civilization.getName());
            return;
        }

        civilization.setOwner(citizen.getChunkyPlayer(), true, false);
        civilization.claimChunk(civChunk).setHomeChunk(civChunk.getChunkyChunk()).save();
    }
}
