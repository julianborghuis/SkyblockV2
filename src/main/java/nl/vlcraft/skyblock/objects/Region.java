package nl.vlcraft.skyblock.objects;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.UUID;

public class Region {

    private double x1, x2, z1, z2;
    private String uuid;

    public Region(double x1, double z1, double x2, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.z2 = z2;
        this.z1 = z1;
        this.uuid = UUID.randomUUID().toString();
    }

    public Region(String uuid, double x1, double z1, double x2, double z2) {
        this.uuid = uuid;
        this.x1 = x1;
        this.x2 = x2;
        this.z2 = z2;
        this.z1 = z1;
    }

    public double getMinX() {
        return x1;
    }

    public double getMaxX() {
        return x2;
    }

    public double getMinZ() {
        return z1;
    }

    public double getMaxZ() {
        return z2;
    }

    public double getCenterX() {
        return (x1 + x2) / 2;
    }

    public double getCenterZ() {
        return (z1 + z2) / 2;
    }

    public String getUuid() {
        return uuid;
    }

    public static int getDefaultHeight() {
        return 70;
    }

    public static World getDefaultWorld() {
        return Bukkit.getWorld("SkyblockIslands");
    }
}
