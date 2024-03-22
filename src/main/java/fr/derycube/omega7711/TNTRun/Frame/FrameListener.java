package fr.derycube.omega7711.TNTRun.Frame;

import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class FrameListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> Frame.getInstance().getBoards().put(event.getPlayer().getUniqueId(), new FrameBoard(event.getPlayer())), 20);
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        Frame.getInstance().getBoards().remove(event.getPlayer().getUniqueId());
    }
}