package fr.derycube.omega7711.TNTRun.Handlers;

import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerLeave implements Listener {
    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent e) {
        if(GameManager.players.get(e.getPlayer().getUniqueId()).isMod()) {
            e.setQuitMessage("");
        } else {
            if(GameManager.gamemoment == GameMoment.ROUND) {
                for(Player player:Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatUtil.translate(Utils.gettext(player, Texts.PlayerEliminated).replace("%p", e.getPlayer().getName())));
                }
                e.setQuitMessage("");
                GameManager.checkPlayers();
            } else {
                e.setQuitMessage(ChatUtil.translate("&8[&c-&8] &f"+e.getPlayer().getName()));
            }
        }
        GameManager.players.remove(e.getPlayer().getUniqueId());
        HashMap<UUID, GamePlayer> playersalive = new HashMap<>();
        for(Map.Entry<UUID, GamePlayer> entry:GameManager.players.entrySet()) {
            if(entry.getValue().isAlive()) {
                playersalive.put(entry.getKey(),entry.getValue());
            }
        }
    }
}
