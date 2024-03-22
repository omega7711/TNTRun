package fr.derycube.omega7711.TNTRun.utils;

import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import lombok.Getter;
import org.bukkit.GameMode;


/**
 * Les teams pour être enregistré dans le {@link GamePlayer}.
 */
@Getter
public enum PlayerTeam {
    SURVIVOR(GameMode.ADVENTURE),
    SPECTATOR(GameMode.ADVENTURE),
    MODERATION_MOD(GameMode.CREATIVE);
    final GameMode Gamemode;
    PlayerTeam(GameMode gameModForTeam) {
        this.Gamemode = gameModForTeam;
    }
    public String toString() {
        return "PlayerTeam("+this.getDeclaringClass().getName()+")";
    }
}
