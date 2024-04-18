package fr.derycube.omega7711.TNTRun.hosts;

import fr.derycube.*;

import fr.derycube.command.annotations.*;
import fr.derycube.utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class HostCommands {

    @Command(names = {"h", "h 1", "h help"})
    public static void hostHelp(Player player) {
        player.sendMessage(ChatUtil.translate("&8&m--------------------------------"));
        player.sendMessage(ChatUtil.translate("&8┃ &6&lCommandes de Host"));
        player.sendMessage(ChatUtil.translate("  &e/h ban &f<pseudo> &7(&fBannir quelqu'un de l'host&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h unban &f<pseudo> &7(&fDébannir quelqu'un de l'host&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h banlist &7(&fVoir la liste des bannis&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h kick &f<pseudo> &7(&fExpulser quelqu'un de la partie&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h say &7(&fEnvoyer un message en tant que Host&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h stop &7(&fStopper le serveur&7)"));
        TextComponent next = new TextComponent(ChatUtil.translate("&e&l[» PAGE 2]"));
        next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h 2"));
        player.spigot().sendMessage(
                new TextComponent(ChatUtil.translate("&8┃ &fPage &6&l1 &fsur &6&l2 ")),
                next
        );
        player.sendMessage(ChatUtil.translate("&8&m--------------------------------"));
    }

    @Command(names = {"h 2", "h help 2"})
    public static void hostHelp2(Player player) {
        player.sendMessage(ChatUtil.translate("&8&m--------------------------------"));
        player.sendMessage(ChatUtil.translate("&8┃ &6&lCommandes de Host "));
        player.sendMessage(ChatUtil.translate("  &e/h config &7(&fOuvrir le menu de configuration&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h wl add &f<pseudo> &7(&fAjouter quelqu'un à la whitelist&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h wl remove &f<pseudo> &7(&fRetirer quelqu'un à la whitelist&7)"));
        player.sendMessage(ChatUtil.translate("  &e/h wl list &7(&fVoir la liste des joueurs whitelistés&7)"));
        TextComponent previous = new TextComponent(ChatUtil.translate("&e&l[« PAGE 1]"));
        previous.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h 1"));
        player.spigot().sendMessage(
                new TextComponent(ChatUtil.translate("&8┃ &fPage &6&l2 &fsur &6&l2 ")),
                previous
        );
        player.sendMessage(ChatUtil.translate("&8&m--------------------------------"));
    }

    @Command(names = {"h ban"})
    public static void ban(Player player, @Param(name = "player") Player target, @Param(name = "raison", wildcard = true) String raison) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }


        HostManager.getBanned().add(target.getName());
        player.sendMessage(ChatUtil.prefix("&fVous avez &cbanni &6" + target.getName() + " &fde cet host."));
        target.sendMessage(ChatUtil.prefix("&fVous avez été &cbanni &fpar &6" + player.getName() + " &fpour &c" + raison));
    }

    @Command(names = {"h unban"})
    public static void unban(Player player, @Param(name = "player") String target) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        if (!HostManager.getBanned().contains(target)) {
            player.sendMessage(ChatUtil.prefix("&cCe joueur n'est pas banni de vos hosts"));
            return;
        }

        HostManager.getBanned().remove(target);
        player.sendMessage(ChatUtil.prefix("&fVous avez &adébanni &6" + target + " &fde cet host."));
    }


    @Command(names = {"h banlist"})
    public static void banList(Player player) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        if (HostManager.getBanned().isEmpty()) {
            player.sendMessage(ChatUtil.prefix("&cPersonne n'est banni"));
            return;
        }

        player.sendMessage(" ");
        player.sendMessage(ChatUtil.translate("&8» &c&lBan-List"));
        HostManager.getBanned().forEach(s -> player.sendMessage(ChatUtil.translate(" &7■ &f" + s)));
        player.sendMessage(" ");
    }

    @Command(names = {"h kick"})
    public static void kick(Player player, @Param(name = "player") Player target, @Param(name = "raison", wildcard = true) String raison) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        target.sendMessage(ChatUtil.prefix("&fVous avez été &ckick &fpar &6" + player.getName() + " &fpour &c" + raison));
        player.sendMessage(ChatUtil.prefix("&fVous avez &ckick &6" + target.getName() + " &fde la partie"));
    }

    @Command(names = {"h config"})
    public static void config(Player player) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        new HostMenu().openMenu(player);
    }

    @Command(names = {"h whitelist add", "h wl add"})
    public static void whitelistAdd(Player player, @Param(name = "player") String target) {

        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        if (HostManager.getWhitelistedPlayers().contains(target)) {
            player.sendMessage(ChatUtil.prefix("&cCe joueur est déjà whitelist"));
            return;
        }
        HostManager.getWhitelistedPlayers().add(target);
        BukkitAPI.getDerycubeAPI().getServerCache().getMinijeuxServers().get(Bukkit.getPort()).getWhitelistedPlayers().add(target);
        player.sendMessage(ChatUtil.prefix("&fVous avez &cwhitelist &fle joueur &c" + target));
    }

    @Command(names = {"h whitelist remove", "h wl remove"})
    public static void whitelistRemove(Player player, @Param(name = "player") String target) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        if (!HostManager.getWhitelistedPlayers().contains(target)) {
            player.sendMessage(ChatUtil.prefix("&cCe joueur est déjà whitelist"));
            return;
        }

        HostManager.getWhitelistedPlayers().remove(target);
        BukkitAPI.getDerycubeAPI().getServerCache().getMinijeuxServers().get(Bukkit.getPort()).getWhitelistedPlayers().remove(target);
        player.sendMessage(ChatUtil.prefix("&fVous avez retiré &c" + target + " &fde la &cwhitelist"));
    }

    @Command(names = {"h whitelist list", "h wl list"})
    public static void whitelistList(Player player) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        if (HostManager.getWhitelistedPlayers().isEmpty()) {
            player.sendMessage(ChatUtil.prefix("&cPersonne n'est whitelist"));
            return;
        }

        player.sendMessage(" ");
        player.sendMessage(ChatUtil.translate("&8» &c&lWhitelist-List"));
        HostManager.getWhitelistedPlayers().forEach(s -> player.sendMessage(ChatUtil.translate(" &7■ &f" + s)));
        player.sendMessage(" ");
    }

    @Command(names = {"h say", "say"})
    public static void onCommand(Player player, @Param(name = "message", wildcard = true) String message) {
        if (!HostManager.getHoster().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ChatUtil.prefix("&cVous n'avez pas la permission d'exécuter cette commande"));
            return;
        }

        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(ChatUtil.translate("&7❘ &e&lHOST &e" + player.getName() + " &8» &f" + message));
        Bukkit.broadcastMessage(" ");
    }

}