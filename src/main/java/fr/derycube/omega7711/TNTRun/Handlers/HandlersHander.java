package fr.derycube.omega7711.TNTRun.Handlers;

import org.bukkit.plugin.java.JavaPlugin;

public class HandlersHander {
    public static void setupHandler(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerLeave(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryHandler(), plugin);
    }
}
