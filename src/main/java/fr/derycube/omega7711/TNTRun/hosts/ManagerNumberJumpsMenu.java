package fr.derycube.omega7711.TNTRun.hosts;

import fr.derycube.menu.Button;
import fr.derycube.menu.Menu;
import fr.derycube.menu.buttons.BackButton;
import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.utils.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ManagerNumberJumpsMenu extends Menu {

    private final Menu oldMenu;

    @Override
    public String getTitle(Player player) {
        return "Round Length";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new ChangeRoundTimerButton(-10, 1));
        buttons.put(1, new ChangeRoundTimerButton(-5, 14));
        buttons.put(2, new ChangeRoundTimerButton(-1, 11));

        buttons.put(4, new HostMenu.NumberofJumps());

        buttons.put(6, new ChangeRoundTimerButton(1, 12));
        buttons.put(7, new ChangeRoundTimerButton(5, 10));
        buttons.put(8, new ChangeRoundTimerButton(10, 2));

        if (oldMenu != null) {
            buttons.put(13, new BackButton(oldMenu));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private static class ChangeRoundTimerButton extends Button {

        private final int add;
        private final int durability;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.BANNER).setDurability(durability).setName(add > 0 ? "&a+" + add : "&c" + add).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (GameManager.getDoublejumpsatstartofgame() + add <= 0) {
                GameManager.setDoublejumpsatstartofgame(0);
                return;
            }
            GameManager.setDoublejumpsatstartofgame(Math.min(GameManager.getDoublejumpsatstartofgame() + add, 10));
        }

        @Override
        public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

}