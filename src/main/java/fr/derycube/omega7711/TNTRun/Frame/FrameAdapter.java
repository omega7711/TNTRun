package fr.derycube.omega7711.TNTRun.Frame;

import org.bukkit.entity.*;

import java.util.*;

public interface FrameAdapter
{
    String getTitle(final Player p0);
    
    List<String> getLines(final Player p0);
}
