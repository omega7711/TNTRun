package fr.derycube.omega7711.TNTRun.Frame;

import lombok.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.*;
import org.bukkit.scoreboard.*;

import java.util.*;

@Getter
public class Frame
{
    private static Frame frame;
    private final JavaPlugin plugin;
    @Getter
    private final FrameAdapter adapter;
    @Getter
    private final Map<UUID, FrameBoard> boards;
    private final ScoreboardAnimation.AddressAnimation addressAnimation;
    private final ScoreboardAnimation.TitleAnimation titleAnimation;

    public Frame(final JavaPlugin plugin, final FrameAdapter adapter) {
        if (Frame.frame != null) {
            throw new RuntimeException("Frame a déjà été lancé !");
        }
        Frame.frame = this;
        this.plugin = plugin;
        this.adapter = adapter;
        this.addressAnimation = new ScoreboardAnimation.AddressAnimation("play.derycube.fr");
        this.titleAnimation = new ScoreboardAnimation.TitleAnimation("TNTRun");
        this.boards = new HashMap<>();
        this.setup();
    }

    private void setup() {

        this.plugin.getServer().getPluginManager().registerEvents(new FrameListener(), this.plugin);
        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                try {
                    FrameBoard board = this.boards.get(player.getUniqueId());
                    if (board == null)
                        continue;
                    Scoreboard scoreboard = board.getScoreboard();
                    Objective objective = board.getObjective();
                    String title = titleAnimation.getColorTitle();
                    if (!objective.getDisplayName().equals(title))
                        objective.setDisplayName(title);
                    List<String> newLines = this.adapter.getLines(player);
                    if (newLines == null || newLines.isEmpty()) {
                        board.getEntries().forEach(FrameBoardEntry::remove);
                        board.getEntries().clear();
                    } else {
                        Collections.reverse(newLines);
                        if (board.getEntries().size() > newLines.size())
                            for (int j = newLines.size(); j < board.getEntries().size(); j++) {
                                FrameBoardEntry entry = board.getEntryAtPosition(j);
                                if (entry != null)
                                    entry.remove();
                            }
                        for (int i = 0; i < newLines.size(); i++) {
                            FrameBoardEntry entry = board.getEntryAtPosition(i);
                            String line = ChatColor.translateAlternateColorCodes('&', newLines.get(i));
                            if (entry == null)
                                entry = new FrameBoardEntry(board, line);
                            entry.setText(line);
                            entry.setup();
                            entry.send(i);
                        }
                    }
                    player.setScoreboard(scoreboard);
                } catch (Exception ignored) {}
            }
        }, 1L, 2L);
    }

    public static Frame getInstance() {
        return Frame.frame;
    }
}