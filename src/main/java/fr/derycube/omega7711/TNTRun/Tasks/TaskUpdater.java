package fr.derycube.omega7711.TNTRun.Tasks;

import fr.derycube.BukkitAPI;
import fr.derycube.api.data.server.impl.MiniJeuxServer;
import fr.derycube.api.messaging.packets.MiniJeuxUpdatePacket;
import fr.derycube.omega7711.TNTRun.hosts.HostManager;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import org.bukkit.Bukkit;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TaskUpdater extends BukkitRunnable {
    public TaskUpdater(JavaPlugin plugin) {
        this.runTaskTimer(plugin, 40, 20);
    }

    @Override
    public void run() {
        MiniJeuxServer.ServerStatus type = GameManager.status;
        MiniJeuxServer miniJeuxServer = new MiniJeuxServer(Bukkit.getPort(), MiniJeuxServer.ServerType.TNTRUN, type, GameManager.maxplayers, HostManager.isWhitelist(), Utils.getAlivePlayersExceptMods(true), HostManager.isHost(), (HostManager.getHoster() != null ? HostManager.getHoster() : "null"), HostManager.getWhitelistedPlayers());
        try {
            BukkitAPI.getDerycubeAPI().getMessaging().sendPacket(new MiniJeuxUpdatePacket(miniJeuxServer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
