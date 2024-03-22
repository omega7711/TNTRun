package fr.derycube.omega7711.TNTRun.commands;

import fr.derycube.command.annotations.Command;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import org.bukkit.entity.Player;

public class startCommand {
    @Command(names={"startgame","forcestart"}, power=15)
    public static void startgamecommand(Player player) {
        GameManager.gamestartforced = true;
        GameManager.gamemoment = GameMoment.STARTING;
        GameManager.secondsbeforestart = GameMoment.STARTING.getTimeinSeconds();
        GameManager.cooldownbeforestart = true;
    }
}
