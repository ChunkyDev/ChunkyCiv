package org.getchunky.chunkyciv.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunkyciv.CivManager;
import org.getchunky.chunkyciv.locale.Language;
import org.getchunky.chunkyciv.object.ChunkyCitizen;
import org.getchunky.chunkyciv.object.ChunkyNation;

/**
 * @author dumptruckman
 */
public class CmdNationSetBorders implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        ChunkyCitizen citizen = CivManager.getCitizen(ChunkyManager.getChunkyPlayer((Player) sender));

        if (!citizen.hasNation()) {
            Language.NO_NAT.bad(sender);
            return;
        }

        ChunkyNation nation = citizen.getNation();
        if (!nation.isOwnedBy(citizen.getChunkyPlayer())) {
            sender.sendMessage("You may not set border policy for your nation!");
            return;
        }

        if (args.length < 1) {
            Language.CMD_NAT_SET_BORDERS_HELP.bad(sender);
            return;
        }

        if (args[0].equalsIgnoreCase("open")) {
            nation.setOpenBorders(true);
            sender.sendMessage("Borders open!");
        } else if (args[0].equalsIgnoreCase("closed")) {
            nation.setOpenBorders(false);
            sender.sendMessage("Borders closed!");
        } else {
            Language.CMD_NAT_SET_BORDERS_HELP.bad(sender);
        }
    }
}
