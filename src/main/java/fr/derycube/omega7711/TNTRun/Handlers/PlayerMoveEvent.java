package fr.derycube.omega7711.TNTRun.Handlers;

import fr.derycube.omega7711.TNTRun.managers.GameManager;
import fr.derycube.omega7711.TNTRun.managers.GamePlayer;
import fr.derycube.omega7711.TNTRun.plugin.Plugin;
import fr.derycube.omega7711.TNTRun.utils.GameMoment;
import fr.derycube.omega7711.TNTRun.utils.PlayerTeam;
import fr.derycube.omega7711.TNTRun.utils.Texts;
import fr.derycube.omega7711.TNTRun.utils.Utils;
import fr.derycube.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerMoveEvent implements Listener {
    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent e) {
        GamePlayer player = GameManager.players.get(e.getPlayer().getUniqueId());
        if(GameManager.gamemoment == GameMoment.ROUND) {
            checktoremoveblock(player, e.getTo());
            if(e.getTo().getY() < 13) {
                if(player.getTeam() == PlayerTeam.SURVIVOR) {
                    for(Player bplayer: Bukkit.getOnlinePlayers()) {
                        bplayer.sendMessage(ChatUtil.translate(Utils.gettext(bplayer, Texts.PlayerEliminated).replace("%p", e.getPlayer().getName())));
                    }
                    player.kill();
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(true);
                    e.getPlayer().teleport(GameManager.mapcenter);
                    GameManager.checkPlayers();
                } else if(player.getTeam() == PlayerTeam.SPECTATOR) {
                    e.getPlayer().teleport(GameManager.mapcenter);
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(true);
                }
            }
        }

    }
    public static void checktoremoveblock(GamePlayer player, Location loc) {
        if(player.isAlive()) {
            if(loc.clone().subtract(0, 2, 0).getBlock().getType() == Material.WOOL) {
                ScheduleFloorDestroy sfd = new ScheduleFloorDestroy(loc.clone().subtract(0, 2, 0));
                Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), sfd, GameManager.tickstodestroyblock);
            } else {
                Block blockalongside = getBlockAlongSideLoc(loc.clone().subtract(0,2,0));
                if(blockalongside.getType() != Material.AIR) {
                    ScheduleFloorDestroy sfd = new ScheduleFloorDestroy(blockalongside.getLocation());
                    Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), sfd, GameManager.tickstodestroyblock);
                } else {
                    Block blockindiagonal = getBlockInDiagonalsOfLoc(loc.clone().subtract(0,2,0));
                    if(blockindiagonal.getType() != Material.AIR) {
                        ScheduleFloorDestroy sfd = new ScheduleFloorDestroy(blockindiagonal.getLocation());
                        Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), sfd, GameManager.tickstodestroyblock);
                    }
                }
            }
        }
    }
    private static Block getBlockAlongSideLoc(Location loc) {
        if(loc.clone().add(0.3,0,0).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(0.3,0,0).getBlock();
        } else if (loc.clone().add(0,0,0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(0,0,0.3).getBlock();
        } else if (loc.clone().add(-0.3,0,0).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(-0.3,0,0).getBlock();
        } else if (loc.clone().add(0,0,-0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(0,0,-0.3).getBlock();
        } else {
            return loc.getBlock();
        }
    }
    private static Block getBlockInDiagonalsOfLoc(Location loc) {
        if(loc.clone().add(0.3,0,0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(0.3,0,0.3).getBlock();
        } else if (loc.clone().add(-0.3,0,-0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(-0.3,0,-0.3).getBlock();
        } else if (loc.clone().add(-0.3,0,0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(-0.3,0,0.3).getBlock();
        } else if (loc.clone().add(0.3,0,-0.3).getBlock().getType() == Material.WOOL) {
            return loc.clone().add(0.3,0,-0.3).getBlock();
        } else {
            return loc.getBlock();
        }
    }
    private static class ScheduleFloorDestroy extends BukkitRunnable {
        private final Location upperblock;
        private final Location middleblock;
        private final Location lowerblock;

        public ScheduleFloorDestroy(Location middleblock) {
            this.middleblock = middleblock.clone();
            this.upperblock = middleblock.clone().add(0, 1, 0);
            this.lowerblock = middleblock.clone().add(0, -1, 0);
        }
        @Override
        public void run() {
            if(GameManager.gamemoment == GameMoment.ROUND) {
                upperblock.clone().getBlock().setType(Material.AIR);
                middleblock.clone().getBlock().setType(Material.AIR);
                lowerblock.clone().getBlock().setType(Material.AIR);
            }
        }
    }
}
