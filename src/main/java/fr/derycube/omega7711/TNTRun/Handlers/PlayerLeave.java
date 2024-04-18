package fr.derycube.omega7711.TNTRun.Handlers;

import fr.derycube.omega7711.TNTRun.hosts.HostManager;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import fr.derycube.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
        if(HostManager.getHoster().equalsIgnoreCase(e.getPlayer().getName())) {
            HostManager.setDisconnect(5 * 60);
            for(Player player:Bukkit.getOnlinePlayers()) {
                player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.HostDisconnected).replace("%p", e.getPlayer().getName()).replace("%s", TimeUtil.niceTime(HostManager.getDisconnect() * 1000L))));
            }
            //Bukkit.broadcastMessage(ChatUtil.prefix("&c" + e.getPlayer().getName() + " &fs'est déconnecté. Il a &c" + TimeUtil.niceTime(HostManager.getDisconnect() * 1000L) + " minutes &fpour se reconnecter ou l'host sera &cfermé&f."));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Bukkit.getPlayer(e.getPlayer().getUniqueId()) != null) {
                        HostManager.setDisconnect(5*60);
                        cancel();
                        return;
                    }
                    HostManager.setDisconnect(HostManager.getDisconnect() - 1);
                    if (HostManager.getDisconnect() <= 0) {
                        Bukkit.getServer().shutdown();
                    }
                }
            }.runTaskTimer(Plugin.getInstance(), 0, 20);
        }
    }
}
