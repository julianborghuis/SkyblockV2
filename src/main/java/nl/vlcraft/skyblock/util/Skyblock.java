package nl.vlcraft.skyblock.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.math.transform.AffineTransform;
import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.database.Database;
import nl.vlcraft.skyblock.objects.Island;
import nl.vlcraft.skyblock.objects.Region;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Skyblock {

    public static Island createNewIsland(Main main, Player player, String type) {
        Region islandRegion = RegionManager.generateNewRegion(main);

        String uuid = UUID.randomUUID().toString();
        String regionUID = islandRegion.getUuid();
        Location islandCenter = new Location(Region.getDefaultWorld(),
                islandRegion.getCenterX(),
                Region.getDefaultHeight(),
                islandRegion.getCenterZ());
        String homeLocation = SerializationManager.locationToString(islandCenter);
        String biome = Biome.PLAINS.name();
        double balance = 0;
        int level = 0;
        String owner = player.getUniqueId().toString();

        Database.setConnection(main);
        try {
            Statement stmt = main.con.createStatement();

            stmt.execute("INSERT INTO `islands` VALUES ('" +
                    uuid + "', '" +
                    regionUID + "', '" +
                    type + "', '" +
                    homeLocation + "', '" +
                    biome + "', '" +
                    balance + "', '" +
                    level + "', '" +
                    null + "', '" +
                    "', '" +
                    /* false */ "0', '" +
                    owner + "', '" +
                    "', '" +
                    "', '" +
                    "', '" +
                    "', '" +
                    "');");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RegionManager.saveRegion(main, islandRegion);
        pasteIsland(getIslandPath(type), islandCenter);

        return new Island(uuid, islandRegion);
    }

    private static String getIslandPath(String islandType) {
        if(islandType.equals("speler")) return "plugins/WorldEdit/schematics/island_1.schematic";
        else if(islandType.equals("fan")) return "plugins/WorldEdit/schematics/island_23.schematic";
        else if(islandType.equals("goeiefan")) return "plugins/WorldEdit/schematics/island_23.schematic";
        else if(islandType.equals("superfan")) return "plugins/WorldEdit/schematics/island_4.schematic";
        else if(islandType.equals("nakker")) return "plugins/WorldEdit/schematics/island_5.schematic";
        else if(islandType.equals("legende")) return "plugins/WorldEdit/schematics/island_6.schematic";
        else return "";
    }

    private static void pasteIsland(String path, Location pasteLocation) {
        try {
            File file = new File(path);
            Vector to = new Vector(pasteLocation.getX(), pasteLocation.getY(), pasteLocation.getZ());

            com.sk89q.worldedit.world.World weWorld = new BukkitWorld(pasteLocation.getWorld());

            Clipboard clipboard = ClipboardFormat.SCHEMATIC.getReader(new java.io.FileInputStream(file)).read(weWorld.getWorldData());
            com.sk89q.worldedit.regions.Region region = clipboard.getRegion();

            EditSession extent = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, 1);
            AffineTransform transform = new AffineTransform();

            ForwardExtentCopy copy = new ForwardExtentCopy(clipboard, region, clipboard.getOrigin(), extent, to);
            if (!transform.isIdentity()) copy.setTransform(transform);
            copy.setSourceMask(new com.sk89q.worldedit.function.mask.ExistingBlockMask(clipboard));
            com.sk89q.worldedit.function.operation.Operations.completeLegacy(copy);
            extent.flushQueue();
        } catch (IOException | MaxChangedBlocksException e) {
            System.out.println("Pasting schematic failed.");
        }
    }

    private static void createBarrierCube(Location corner1, Location corner2) {
        com.sk89q.worldedit.world.World weWorld = new BukkitWorld(corner1.getWorld());

        com.sk89q.worldedit.regions.Region region = weWorld.

        EditSession extent = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, 1);
        extent.makeWalls()
    }
}
