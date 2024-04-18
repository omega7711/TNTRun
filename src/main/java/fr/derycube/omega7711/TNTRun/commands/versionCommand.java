package fr.derycube.omega7711.TNTRun.commands;

import fr.derycube.command.annotations.Command;
import fr.derycube.utils.ChatUtil;
import org.bukkit.entity.Player;

public class versionCommand {
    @Command(names={"ver","version"}, power=15)
    public static void startgamecommand(Player player) {
        player.sendMessage(ChatUtil.prefix("This server is running the version &cBeta-18.04.24-Build4 &fof &c&lTNTRUN"));
    }
}
