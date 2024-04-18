package fr.derycube.omega7711.TNTRun.managers;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import fr.derycube.BukkitAPI;
import fr.derycube.api.data.player.ProfileData;
import fr.derycube.manager.stats.StatsProfileData;
import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.utils.ChatUtil;
import fr.derycube.utils.item.CustomItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.UUID;

@Getter
@Setter
public class GamePlayer {
    private String username;
    private int points;
    private int WinStreak;
    private final UUID uuid;
    private PlayerTeam team;
    private boolean Mod;
    private boolean alive;
    private StatsProfileData statsprofile;
    private boolean showDeads;
    private int doublejump;

    public GamePlayer(Player player) {
        this.uuid = player.getUniqueId();
        ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(this.uuid);
        this.username = profile.getDisplayName();
        this.statsprofile = new StatsProfileData(player);
        this.Mod = profile.isStaff();
        try {
            this.WinStreak = (int) statsprofile.getStatsof(Plugin.MinigameName, "wins_streak");
            this.points = (int) statsprofile.getStatsof(Plugin.MinigameName, "points");
        } catch (Exception e) {
            this.WinStreak = 0;
            this.points = 0;
        }
        this.alive = true;
        this.doublejump = GameManager.doublejumpsatstartofgame;
    }
    public void giveitem() {
        if(this.doublejump > 0 && this.isAlive()) {
            ItemStack is = new ItemStack(Material.FEATHER, this.doublejump);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatUtil.translate("&a&lDouble Saut"));
            is.setItemMeta(im);
            CustomItem ci = new CustomItem(is, im.getDisplayName(), onClick -> {
                if(onClick.isRightClick()) {
                    onClick.getPlayer().setVelocity(new Vector(onClick.getPlayer().getVelocity().getX(), 1, onClick.getPlayer().getVelocity().getZ()));
                    GameManager.players.get(onClick.getPlayer().getUniqueId()).remove1todoublejump();
                    this.giveitem();
                }
            });
            Bukkit.getPlayer(this.uuid).getInventory().setItem(4, ci.toItemStack());
        } else {
            if(this.getPlayer() != null && this.getPlayer().getInventory() != null && this.getPlayer().getInventory().getItem(4) != null && this.getPlayer().getInventory().getItem(4).getType() != null ) {
                if(this.getPlayer().getInventory().getItem(4).getType() == Material.FEATHER) {
                    this.getPlayer().getInventory().setItem(4, new ItemStack(Material.AIR));
                }
            }

        }
    }
    public void remove1todoublejump() {
        this.doublejump -= 1;
    }
    public void addWintoWinStreak() {
        this.setWinStreak(this.getWinStreak()+1);
        statsprofile.setStatson(Plugin.MinigameName, "wins_streak", this.getWinStreak());
    }
    public void resetWinStreak() {
        this.setWinStreak(0);
        statsprofile.setStatson(Plugin.MinigameName, "wins_streak", 0);
    }
    public void win() {
        this.addWintoWinStreak();
        this.addPoints(Utils.getRandomNumberInRange(2, 5));
        this.addCoins(Utils.getRandomNumberInRange(10, 25));
    }
    public void addCoins(int coins) {
        ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(this.uuid);
        profile.setCoins(profile.getCoins()+coins);
        BukkitAPI.getDerycubeAPI().saveProfile(this.uuid,  profile);
        Bukkit.getPlayer(this.uuid).sendMessage(Utils.prefix(Utils.gettext(Bukkit.getPlayer(this.uuid), Texts.YouveGainedCoins).replace("%s", String.valueOf(coins))));
    }
    public void addPoints(int points) {
        this.setPoints(this.getPoints()+points);
        statsprofile.setStatson(Plugin.MinigameName, "points", this.getPoints());
    }
    public void switchDeadsVisibility() {
        if(this.showDeads) {
            this.showDeads = false;
        } else {
            this.showDeads = true;
        }
    }
    public void kill() {
        this.setAlive(false);
        this.resetWinStreak();
        Player player = Bukkit.getPlayer(this.uuid);
        this.setTeam(PlayerTeam.SPECTATOR);
        for(Player player1:Bukkit.getOnlinePlayers()) {
            player1.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
        }
        ParticleNativeAPI api = ParticleNativeCore.loadAPI(Plugin.getInstance());
        api.LIST_1_8.EXPLOSION_HUGE.packet(true, player.getLocation()).sendTo(Bukkit.getOnlinePlayers());
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
    public String toString() {
        return "GamePlayer("+this.uuid.toString()+", "+this.username+", points: "+this.points+", WinStreak: "+this.WinStreak+", Team: "+this.team+", isMod: "+this.isMod()+", Alive: "+this.isAlive()+")";
    }
}
