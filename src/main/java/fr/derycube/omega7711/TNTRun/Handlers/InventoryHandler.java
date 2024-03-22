package fr.derycube.omega7711.TNTRun.Handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class InventoryHandler implements Listener {
    @EventHandler
    public static void onInventoryPickupEvent(InventoryPickupItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public static void onInventoryMoveEvent(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }
}
