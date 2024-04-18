package fr.derycube.omega7711.TNTRun.plugin;

import com.nametagedit.plugin.NametagEdit;
import fr.derycube.BukkitAPI;
import fr.derycube.api.data.player.ProfileData;
import fr.derycube.api.data.server.impl.MiniJeuxServer;
import fr.derycube.omega7711.TNTRun.Frame.Frame;
import fr.derycube.omega7711.TNTRun.Frame.ScoreboardAdapter;
import fr.derycube.omega7711.TNTRun.Handlers.HandlersHander;
import fr.derycube.omega7711.TNTRun.Handlers.PlayerMoveEvent;
import fr.derycube.omega7711.TNTRun.Tasks.TaskUpdater;
import fr.derycube.omega7711.TNTRun.commands.startCommand;
import fr.derycube.omega7711.TNTRun.commands.versionCommand;
import fr.derycube.omega7711.TNTRun.hosts.HostManager;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import fr.derycube.utils.item.CustomItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Plugin extends JavaPlugin {
    public static String MinigameName = "TNTRun";
    @Getter
    public static Plugin instance;
    @Override
    public void onEnable() {
        new Frame(this, new ScoreboardAdapter());
        Plugin.instance = this;
        HandlersHander.setupHandler(this);
        HostManager.registerHost();
        Bukkit.getLogger().info("%s made by omega_7711 for DeryCube (and only)".replace("%s", Plugin.MinigameName));
        Bukkit.getScheduler().runTaskTimer(this, Plugin::custom_tick, 20L, 1L);
        GameManager.setup();
        new TaskUpdater(this);
        BukkitAPI.getCommandHandler().registerCommands(startCommand.class);
        BukkitAPI.getCommandHandler().registerCommands(versionCommand.class);
    }
    public static void custom_tick() {
        Bukkit.getWorld("world").setStorm(false);
        for(Player player:Bukkit.getOnlinePlayers()) {
            if(GameManager.players.isEmpty()) {return;}
            //Crée un "vanish" pour les joueurs en spectateurs
            for(GamePlayer player1: GameManager.players.values()) {
                player1.giveitem();
                if(GameManager.players.get(player.getUniqueId()).isMod()) {break;}
                if(!player1.isAlive()) {
                    ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(player1.getUuid());
                    if(profile.isStaff()) {continue;}
                    player.hidePlayer(Bukkit.getPlayer(player1.getUuid()));
                } else {
                    player.showPlayer(Bukkit.getPlayer(player1.getUuid()));
                }
            }
            if(GameManager.players.containsKey(player.getUniqueId())) {
                if(GameManager.players.get(player.getUniqueId()).isAlive() && GameManager.gamemoment == GameMoment.ROUND) {
                    PlayerMoveEvent.checktoremoveblock(GameManager.players.get(player.getUniqueId()), player.getLocation());
                }
                player.setFoodLevel(20);
                player.setHealth(20);
                //Ajout du isMod ou non dans le GamePlayer si le joueurs est en /mod
                ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(player.getUniqueId());
                if(!profile.isStaff()) {
                    if(GameManager.players.get(player.getUniqueId()).isMod()) {
                        GameManager.players.get(player.getUniqueId()).setTeam(PlayerTeam.SPECTATOR);
                        GameManager.players.get(player.getUniqueId()).setMod(false);
                        GameManager.players.get(player.getUniqueId()).setAlive(true);
                        Bukkit.broadcastMessage(ChatUtil.translate("&8[&a+&8] &f"+player.getName()));
                    }
                } else {
                    if(!GameManager.players.get(player.getUniqueId()).isMod()) {
                        Bukkit.broadcastMessage(ChatUtil.translate("&8[&c-&8] &f"+player.getName()));
                    }
                    GameManager.players.get(player.getUniqueId()).setTeam(PlayerTeam.MODERATION_MOD);
                    GameManager.players.get(player.getUniqueId()).setMod(true);
                }
                //Met les joueurs dans leur GameMode respectif
                player.setGameMode(GameManager.players.get(player.getUniqueId()).getTeam().getGamemode());
                if(GameManager.gamemoment == GameMoment.ROUND) {
                    if(GameManager.players.get(player.getUniqueId()).getTeam()==PlayerTeam.SURVIVOR){
                        player.getInventory().setHelmet(new ItemStack(Material.AIR));
                        NametagEdit.getApi().setPrefix(player, ChatUtil.translate("&f"));
                        NametagEdit.getApi().applyTags();
                        player.removePotionEffect(PotionEffectType.SPEED);
                        player.setAllowFlight(false);
                    } else if(GameManager.players.get(player.getUniqueId()).getTeam()==PlayerTeam.MODERATION_MOD) {
                        player.setAllowFlight(true);
                        NametagEdit.getApi().applyTags();
                    } else if(GameManager.players.get(player.getUniqueId()).getTeam()==PlayerTeam.SPECTATOR) {
                        player.setAllowFlight(true);
                        player.getInventory().setHelmet(new ItemStack(Material.AIR));
                        player.getInventory().setItem(4, new ItemStack(Material.AIR));
                        NametagEdit.getApi().setPrefix(player, ChatUtil.translate("&8&m"));
                        NametagEdit.getApi().applyTags();
                    }
                }
            }
        }
        if(GameManager.players.size()>=GameManager.minplayers&&GameManager.gamemoment== GameMoment.WAITING_FOR_PLAYERS) {
            GameManager.gamemoment = GameMoment.STARTING;
            GameManager.secondsbeforestart = GameMoment.STARTING.getTimeinSeconds();
        }
        if(GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS) {
            if(Utils.getAlivePlayersExceptMods(true)>=GameManager.maxplayers) {
                GameManager.status = MiniJeuxServer.ServerStatus.FULL;
            } else {
                GameManager.status = MiniJeuxServer.ServerStatus.OPENED;
            }
        }
    }
}
