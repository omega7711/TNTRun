package fr.derycube.omega7711.TNTRun.hosts;

import fr.derycube.utils.*;
import fr.derycube.utils.item.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.ArrayList;

public class HostManager {
    @Getter
    private static ArrayList<String> banned = new ArrayList<>();
    @Getter@Setter
    public static boolean whitelist;
    @Getter
    public static ArrayList<String> whitelistedPlayers;
    @Getter
    @Setter
    public static boolean premium = false;
    @Getter
    public static boolean host;
    @Getter
    public static String hoster;
    @Setter@Getter
    public static int disconnect = 60;
    public static void giveHostItems(Player player) {
        player.getInventory().setItem(4,new CustomItem(new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("&6&lConfiguration").toItemStack(),
                "&6&lConfiguration", onClick -> {
            new HostMenu().openMenu(onClick.getPlayer());
        }).toItemStack());
    }
    public static void registerHost() {
        if(HostUtils.containsKey(Bukkit.getPort())) {
            host = true;
            hoster = new HostUtils(Bukkit.getPort()).getHost();
            whitelist = true;
        }else {
            host = false;
            hoster = null;
            whitelist = false;
        }
        whitelistedPlayers = new ArrayList<>();
    }
}