package fr.derycube.omega7711.TNTRun.managers;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import fr.derycube.api.data.server.impl.MiniJeuxServer;
import fr.derycube.omega7711.TNTRun.Handlers.PlayerMoveEvent;
import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import fr.derycube.utils.Title;
import fr.derycube.utils.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class GameManager {
    public static GameMoment gamemoment;
    public static Location mapcenter = new Location(Bukkit.getWorld("world"), -103.5, 48, -802.5, 0,0);
    public static int minplayers = 2;
    public static int maxplayers = 30;
    public static int secondsbeforestart;
    public static boolean cooldownbeforestart;
    public static boolean gamestartforced;
    public static int roundtimer;
    public static HashMap<UUID, GamePlayer> players;
    public static MiniJeuxServer.ServerStatus status;
    public static Long tickstodestroyblock;
    public static void setup() {
        GameManager.players = new HashMap<>();
        GameManager.gamemoment = GameMoment.WAITING_FOR_PLAYERS;
        GameManager.status = MiniJeuxServer.ServerStatus.OPENED;
        GameManager.secondsbeforestart = GameMoment.STARTING.getTimeinSeconds();
        cooldownbeforestart = false;
        gamestartforced = false;
        tickstodestroyblock = 15L;
        Bukkit.getScheduler().runTaskTimer(Plugin.getInstance(), GameManager::secondTick, 20L, 20L);
    }
    public static void secondTick() {
        if(cooldownbeforestart) {
            GameManager.gamemoment = GameMoment.STARTING;
            if(!gamestartforced) {
                if(Utils.getAlivePlayersExceptMods(true)<GameManager.minplayers) {
                    for(Player player:Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.GameStartCancelled)));
                    }
                    cooldownbeforestart = false;
                    secondsbeforestart = GameMoment.STARTING.getTimeinSeconds();
                    GameManager.gamemoment = GameMoment.WAITING_FOR_PLAYERS;
                }
            }
            if(secondsbeforestart > 0) {
                secondsbeforestart = secondsbeforestart-1;
                for(Player player:Bukkit.getOnlinePlayers()) {
                    player.setLevel(secondsbeforestart);
                }
                List<Integer> secondswithsounds = Arrays.asList(15,10,5,4,3,2,1);
                if(secondswithsounds.contains(secondsbeforestart)) {
                    for(Player player:Bukkit.getOnlinePlayers()) {
                        String toreplace = Utils.gettext(player, Texts.s);
                        if(secondsbeforestart == 1) {
                            toreplace = "";
                        }
                        player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.TimeLeft).replace("%ss", toreplace).replace("%s", String.valueOf(GameManager.secondsbeforestart))));
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1+((10-(float) secondsbeforestart)/10));
                    }
                }
                if(secondsbeforestart == 0) {
                    startround();
                }
            } else {
                startround();
            }
        }
        for(Player player:Bukkit.getOnlinePlayers()) {
            if(GameManager.gamemoment == GameMoment.ROUND) {
                GameManager.players.get(player.getUniqueId()).giveitem();
                PlayerMoveEvent.checktoremoveblock(GameManager.players.get(player.getUniqueId()), player.getLocation());
            }
        }

    }
    public static void startround() {
        cooldownbeforestart = false;
        GameManager.gamemoment = GameMoment.ROUND;
        for(GamePlayer player:GameManager.players.values()) {
            if(player.isAlive()) {
                player.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));
            }
        }
    }
    public static void checkPlayers() {
        if(Utils.getAlivePlayersExceptMods(false) <= 1) {
            GamePlayer playerwhowon = null;
            for(GamePlayer player:GameManager.players.values()) {
                if(player.isAlive()) {
                    playerwhowon = player;
                    break;
                }
            }
            if(playerwhowon != null) {
                playerwhowon.win();
            }
            GameManager.gamemoment = GameMoment.GAME_OVER;
            for(Player player:Bukkit.getOnlinePlayers()) {
                player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.GameOver)));
                if(playerwhowon == null) {
                    player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.Unabletofindwhowon)));
                } else {
                    player.sendMessage(Utils.prefix(Utils.gettext(player, Texts.PlayerWonGame).replace("%s", playerwhowon.getUsername())));
                }
            }
            Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), GameManager::kickall, GameMoment.GAME_OVER.getTimeinTicks());
            Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), Bukkit::shutdown, GameMoment.GAME_OVER.getTimeinTicks()+20);
        }
    }
    public static void kickall() {
        for(Player player:Bukkit.getOnlinePlayers()) {
            IPlayerManager manager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);
            manager.getPlayerExecutor(player.getUniqueId()).connectToFallback();
        }
    }
}
