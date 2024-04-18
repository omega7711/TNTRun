package fr.derycube.omega7711.TNTRun.hosts;

import fr.derycube.menu.*;
import fr.derycube.menu.buttons.*;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.utils.*;
import lombok.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

@RequiredArgsConstructor
public class ManagerSlotsMenu extends Menu {

    private final Menu oldMenu;

    @Override
    public String getTitle(Player player) {
        return "Slots";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new ChangeSlotsButton(-10, 1));
        buttons.put(1, new ChangeSlotsButton(-5, 14));
        buttons.put(2, new ChangeSlotsButton(-1, 11));

        buttons.put(4, new HostMenu.SlotsButton());

        buttons.put(6, new ChangeSlotsButton(1, 12));
        buttons.put(7, new ChangeSlotsButton(5, 10));
        buttons.put(8, new ChangeSlotsButton(10, 2));

        if (oldMenu != null) {
            buttons.put(13, new BackButton(oldMenu));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private static class ChangeSlotsButton extends Button {

        private final int add;
        private final int durability;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.BANNER).setDurability(durability).setName(add > 0 ? "&a+" + add : "&c" + add).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (GameManager.getMaxplayers() + add <= 2) {
                GameManager.setMaxplayers(2);
                return;
            }
            GameManager.setMaxplayers(Math.min(GameManager.getMaxplayers() + add, 60));
        }

        @Override
        public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

}