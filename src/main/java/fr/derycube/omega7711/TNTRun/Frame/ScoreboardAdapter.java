package fr.derycube.omega7711.TNTRun.Frame;

import fr.derycube.BukkitAPI;
import fr.derycube.api.data.player.ProfileData;

import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.*;

public class ScoreboardAdapter implements FrameAdapter {

    @Override
    public String getTitle(final Player player) {
        return "&6&lDeryCube";
    }

    @Override
    public List<String> getLines(final Player player) {
        final List<String> toReturn = new ArrayList<>();
        ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(player.getUniqueId());
        GamePlayer player1 = GameManager.players.get(player.getUniqueId());
        toReturn.add(ChatUtil.translate("&8MJ-") + Bukkit.getPort());
        toReturn.add(" ");

        if(GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS) {
            toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.Players)+": &e" + Utils.getAlivePlayersExceptMods() + "&f/&e" + GameManager.maxplayers);
            toReturn.add(" ");
            if(Bukkit.getOnlinePlayers().size() < GameManager.minplayers) {
                toReturn.add("&8┃ &c"+ Utils.gettext(player, Texts.Waitingforxplayers).replace("%s", String.valueOf(GameManager.minplayers-Utils.getAlivePlayersExceptMods(false))));
            }
            toReturn.add(" ");
            toReturn.add("&f&l➥ &6&l"+Utils.gettext(player, Texts.URStats));
            toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.points)+": &e"+player1.getPoints());
            toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.WinStreak)+": &e"+player1.getWinStreak());

        }  else if (GameManager.gamemoment == GameMoment.GAME_OVER) {
            toReturn.add("&8┃ &c&l" + Utils.gettext(player, Texts.GameOver));
            toReturn.add(" ");
            toReturn.add("&f&l➥ &6&l" + Utils.gettext(player, Texts.URStats));
            toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.points)+": &e"+player1.getPoints());
            toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.WinStreak)+": &e"+player1.getWinStreak());

        } else {
            if (GameManager.gamemoment == GameMoment.STARTING) {
                toReturn.add("&8┃ &f" + Utils.gettext(player, Texts.StartingIn).replace("%s", String.valueOf(GameManager.secondsbeforestart)));
            } else if (GameManager.gamemoment == GameMoment.ROUND) {
                toReturn.add("&8┃ &f" + Utils.gettext(player, Texts.inRound));
                toReturn.add("&8┃ &f"+Utils.gettext(player, Texts.PlayersAlive)+": &e"+Utils.getAlivePlayersExceptMods());
            }
            toReturn.add(" ");
        }
        if(GameManager.players.get(player.getUniqueId()).isMod()) {
            toReturn.add(" ");
            toReturn.add("&9&l" + Utils.gettext(player, Texts.ModerationMode));
        }
        toReturn.add(" ");
        toReturn.add(Frame.getInstance().getAddressAnimation().getColorAddress());

        return toReturn;
    }

    /*private String getAdvancement(int experience, Division division) {
        int percentage = experience * 100 / division.getExperience();
        return "  " + ProgressBar.getProgressBar(experience, division.getExperience(), 7, "■", ChatColor.GREEN, ChatColor.GRAY) + "&f " + percentage + "%";
    }*/
}
