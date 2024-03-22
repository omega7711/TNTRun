package fr.derycube.omega7711.TNTRun.Handlers;

import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    @EventHandler
    public static void onEntityDamage(EntityDamageEvent e) {
        if(e.getCause()==EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public static void onEntityAttackedByAnother(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        /*e.setDamage(0);
        if(!GameManager.players.get(e.getDamager().getUniqueId()).isAlive() || !GameManager.players.get(e.getEntity().getUniqueId()).isAlive()) {
            e.setCancelled(true);
        }*/
    }
}
