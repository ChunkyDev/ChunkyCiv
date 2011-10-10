package org.getchunky.chunkyciv.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyCivilization;
import org.getchunky.chunkyciv.permission.Perm;
import org.getchunky.chunkyciv.util.PluginTools;

/**
 * @author dumptruckman
 */
public class CmdCivNew implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Language.IN_GAME.bad(sender);
            return;
        }
        if (!Perm.EXAMPLE.has(sender)) {
            Language.NO_PERM.bad(sender);
            return;
        }

        if (args.length < 1) {
            Language.CMD_CIV_NEW_HELP.bad(sender);
            return;
        }

        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player)sender));

        if (citizen.hasCivilization()) {
            Language.HAS_CIV.bad(sender, citizen.getCivilization().getName());
            return;
        }

        String name = PluginTools.combineStringArray(args);
        ChunkyCivilization civilization = CivManager.createNation(name);
        if (civilization == null) {
            Language.CIV_EXISTS.bad(sender, civilization.getName());
            return;
        }

        
    }
}
