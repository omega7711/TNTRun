package fr.derycube.omega7711.TNTRun.hosts;

import fr.derycube.menu.*;
import fr.derycube.menu.pagination.*;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

public class HostMenu extends GlassMenu {
    @Override
    public int getGlassColor() {
        return 1;
    }

    @Override
    public Map<Integer, Button> getAllButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(4, new WhitelistButton());

        buttons.put(20, new NumberofJumps());

        buttons.put(49, new StartButton());

        buttons.put(51, new StopServerButton());

        buttons.put(47, new SlotsButton());

        return buttons;
    }

    @Override
    public String getTitle(Player player) {
        return "Configuration";
    }

    private static class StopServerButton extends Button {
        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.INK_SACK).setDurability(1).setName("&6&lStopper le serveur").setLore(
                    "&fPermet de supprimer complètement le",
                    "&fserveur",
                    "",
                    "&f&l» &eCliquez-ici pour supprimer"
            ).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!(GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS || GameManager.gamemoment == GameMoment.STARTING)) {
                player.sendMessage(ChatUtil.prefix("&cVous ne pouvez pas utiliser cet item pendant la partie"));
                return;
            }
            new ConfirmationMenu(() -> Plugin.getInstance().getServer().shutdown(), getButtonItem(player), new HostMenu()).openMenu(player);

        }
    }

    public static class SlotsButton extends Button{
        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.SKULL_ITEM).setDurability(SkullType.PLAYER.ordinal())
                    .setName("&6&lGestion des Slots").setLore(
                            "&fPermet de modifier les slots pour la",
                            "&fpartie.",
                            "",
                            "&8┃ &7Configuration: &c" + GameManager.getMaxplayers(),
                            "",
                            "&f&l» &eCliquez-ici pour y accéder"
                    ).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new ManagerSlotsMenu(new HostMenu()).openMenu(player);
        }
    }
    public static class NumberofJumps extends Button{
        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.FEATHER)
                    .setName("&6&lGestion du nombre de double sauts").setLore(
                            "&fPermet de modifier le nombre de ",
                            "&fdouble sauts qu'un joueur peut",
                            "&ffaire durant la partie.",
                            "",
                            "&8┃ &7Configuration: &c" + GameManager.getDoublejumpsatstartofgame(),
                            "",
                            "&f&l» &eCliquez-ici pour y accéder"
                    ).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new ManagerNumberJumpsMenu(new HostMenu()).openMenu(player);
        }
    }

    private static class WhitelistButton extends Button {
        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.NAME_TAG).setName("&6&lAccessibilité de la partie").setLore(
                    "&fPermet de modifier l'accessibilité à la",
                    "&fpartie pour les joueurs.",
                    " ",
                    "&8┃ &7Accessibilité: " + (HostManager.isWhitelist() ? "&cFermé" : "&aOuvert"),
                    "",
                    "&f&l» &eCliquez-ici pour modifier"
            ).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            HostManager.setWhitelist(!HostManager.isWhitelist());
        }

        @Override
        public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

    private static class StartButton extends Button {
        @Override
        public ItemStack getButtonItem(Player player) {
            if (GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS) {
                return new ItemBuilder(Material.EMERALD_BLOCK).setName("&a&lLancer la partie").setLore(
                        "&fPermet de lancer la partie si tout est",
                        "&fprêt.",
                        "",
                        "&f&l» &eCliquez-ici pour lancer"
                ).addEnchant(Enchantment.DAMAGE_ALL, 1).hideItemFlags().toItemStack();
            } else {
                return new ItemBuilder(Material.REDSTONE_BLOCK).setName("&c&lAnnuler le lancement").setLore(
                        "&fPermet d'annuler le lancement de la",
                        "&fpartie.",
                        "",
                        "&f&l» &eCliquez-ici pour annuler"
                ).addEnchant(Enchantment.DAMAGE_ALL, 1).hideItemFlags().toItemStack();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if(GameManager.gamemoment == GameMoment.WAITING_FOR_PLAYERS) {
                GameManager.roundtimer = GameMoment.STARTING.getTimeinSeconds();
                if(Utils.getAlivePlayersExceptMods() < GameManager.minplayers) {
                    player.sendMessage(Utils.prefix("&c"+Utils.gettext(player, Texts.NotEnoughPlayerstoStart).replace("%s", String.valueOf(GameManager.minplayers))));
                } else {
                    GameManager.gamestartforced = true;
                    GameManager.gamemoment = GameMoment.STARTING;
                    GameManager.cooldownbeforestart = true;
                }
            } else {
                GameManager.gamemoment = GameMoment.WAITING_FOR_PLAYERS;
                GameManager.cooldownbeforestart = false;
            }
        }

        @Override
        public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }
}