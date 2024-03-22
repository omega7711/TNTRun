package fr.derycube.omega7711.TNTRun.Handlers;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import fr.derycube.BukkitAPI;
import fr.derycube.api.data.player.ProfileData;
import fr.derycube.menu.pagination.ConfirmationMenu;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import fr.derycube.utils.ItemBuilder;
import fr.derycube.utils.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 255, false, false));
        ProfileData profile = BukkitAPI.getDerycubeAPI().getProfile(e.getPlayer().getUniqueId());
        GamePlayer player = new GamePlayer(e.getPlayer());
        if(profile.isStaff()) {
            e.setJoinMessage("");
            player.setMod(true);
            player.setAlive(false);
            player.setTeam(PlayerTeam.MODERATION_MOD);
        } else {
            e.setJoinMessage(ChatUtil.translate("&8[&a+&8] &f"+e.getPlayer().getName()));
            player.setMod(false);
            player.setAlive(true);
            player.setTeam(PlayerTeam.SURVIVOR);
            if(GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS || GameManager.gamemoment == GameMoment.STARTING) {
                e.getPlayer().getInventory().clear();
                CustomItem FALLBACK = new CustomItem(Material.BED, Utils.gettext(e.getPlayer(), Texts.ReturnToLobby), onClick -> {
                    new ConfirmationMenu(() -> {
                        IPlayerManager manager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);
                        manager.getPlayerExecutor(onClick.getPlayer().getUniqueId()).connectToFallback();
                    }, new ItemBuilder(Material.BED).setName("&c&l"+Utils.gettext(e.getPlayer(), Texts.ReturnToLobby)).toItemStack(), null).openMenu(onClick.getPlayer());
                });
                e.getPlayer().getInventory().setItem(8, FALLBACK.toItemStack());
            }
        }
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 9999999, 255, false, false), true);
        e.getPlayer().teleport(GameManager.mapcenter.clone());
        GameManager.players.put(e.getPlayer().getUniqueId(), player);
        if(Utils.getAlivePlayersExceptMods(true)>=GameManager.minplayers&&!GameManager.cooldownbeforestart) {
            GameManager.cooldownbeforestart = true;
            GameManager.secondsbeforestart = GameMoment.STARTING.getTimeinSeconds();
        }
    }
}
