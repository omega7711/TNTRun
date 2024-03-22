package fr.derycube.omega7711.TNTRun.Handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {
    @EventHandler
    public static void onBlockBreakEvent(org.bukkit.event.block.BlockBreakEvent e) {
        e.setCancelled(true);
    }
}
