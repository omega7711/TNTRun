package fr.derycube.omega7711.TNTRun.utils;

import fr.derycube.BukkitAPI;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Utils {
    public static String gettext(Player player, Texts texttoget) {
        String language = BukkitAPI.getDerycubeAPI().getProfile(player.getUniqueId()).getLanguage();
        if(Objects.equals(texttoget.getEnglish(), "") && Objects.equals(texttoget.getFrench(), "") && Objects.equals(texttoget.getTkt(), "")) {
            if(language.equalsIgnoreCase("fr")) {
                return texttoget.getFrench_list().get(Utils.getRandomNumberInRange(0, texttoget.getFrench_list().size()));
            } else if (language.equalsIgnoreCase("en")) {
                return texttoget.getEnglish_list().get(Utils.getRandomNumberInRange(0, texttoget.getEnglish_list().size()));
            } else {
                return texttoget.getTkt_list().get(Utils.getRandomNumberInRange(0, texttoget.getTkt_list().size()));
            }
        } else {
            if(language.equalsIgnoreCase("fr")) {
                return texttoget.getFrench();
            } else if(language.equalsIgnoreCase("en")) {
                return texttoget.getEnglish();
            } else {
                return texttoget.getTkt();
            }
        }
    }
    public static List<String> getAllStrings(Player player, Texts texttoget) {
        String language = BukkitAPI.getDerycubeAPI().getProfile(player.getUniqueId()).getLanguage();
        if(language.equalsIgnoreCase("fr")) {
            return texttoget.getFrench_list();
        } else if(language.equalsIgnoreCase("en")) {
            return texttoget.getEnglish_list();
        } else {
            return texttoget.getTkt_list();
        }
    }
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("le max doit être plus grand que le min");
        }

        return (int) Math.floor(Math.random() *(max - min + 1) + min);
    }
    public static boolean isInRegion(Location loctocheck, Region region) {
        Location bound1 = region.from;
        Location bound2 = region.to;
        return loctocheck.getBlockX() >= Math.min(bound1.getBlockX(), bound2.getBlockX()) &&
                loctocheck.getBlockY() >= (Math.min(bound1.getBlockY(), bound2.getBlockY())-0.5) &&
                loctocheck.getBlockZ() >= Math.min(bound1.getBlockZ(), bound2.getBlockZ()) &&
                loctocheck.getBlockX() <= Math.max(bound1.getBlockX(), bound2.getBlockX()) &&
                loctocheck.getBlockY() <= (Math.max(bound1.getBlockY(), bound2.getBlockY())) &&
                loctocheck.getBlockZ() <= Math.max(bound1.getBlockZ(), bound2.getBlockZ());
    }
    public static int getAlivePlayersExceptMods(boolean includeDeads) {
        int tempnumber = 0;
        for(UUID puuid:GameManager.players.keySet()) {
            GamePlayer player = GameManager.players.get(puuid);
            if(player.isAlive() && !player.isMod()) {
                tempnumber++;
            }
        }
        return tempnumber;
    }
    public static int getnumberoftnttoput() {
        int numofpl = getAlivePlayersExceptMods(false);
        if(numofpl >=30) {
            return Values.THIRTY.getNumoftnt();
        } else if(numofpl >=20) {
            return Values.TWENTY.getNumoftnt();
        } else if(numofpl >= 10) {
            return Values.TEN.getNumoftnt();
        } else if(numofpl >= 5) {
            return Values.FIVE.getNumoftnt();
        } else return Values.ZERO.getNumoftnt();
    }
    public static boolean isPair(int number) {
        return (float)number/2!=Math.floor((float)number/2);
    }
    public static int getAlivePlayersExceptMods() {
        return getAlivePlayersExceptMods(false);
    }
    public static boolean canbypass(Player player) {
        return GameManager.players.get(player.getUniqueId()).isMod()|| GameManager.players.get(player.getUniqueId()).getTeam() == PlayerTeam.SPECTATOR;
    }
    public static String prefix(String original) {
        return ChatUtil.translate("&c&lTNT&f&lRUN &8» &f"+original);
    }
}
