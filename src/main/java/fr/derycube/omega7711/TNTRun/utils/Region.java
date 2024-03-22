package fr.derycube.omega7711.TNTRun.utils;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public class Region {
    Location from;
    Location to;
    /**
     * Permet de créer une région à partir de deux coins;
     */
    public Region(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public Region toWalkRegion() {
        return new Region(this.getFrom().clone().subtract(5, 0, 5), this.getTo().clone().add(5, 9, 5));
    }
    public String toString() {
        Location from = this.getFrom();
        Location to = this.getTo();
        return "Region(["+from.getX()+", "+from.getY()+", "+from.getZ()+"], ["+to.getX()+", "+to.getY()+", "+to.getZ()+"])";
    }
}
