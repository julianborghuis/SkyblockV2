package nl.vlcraft.skyblock.util;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.database.Database;
import nl.vlcraft.skyblock.objects.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegionManager {

    private static final int baseX = 500;
    private static final int baseZ = 500;

    private static final int islandSize = 200;
    private static final int deltaIsland = 50;

    public static Region generateNewRegion(Main main) {
        String lastIsland = main.getConfig().getString("Data.last_island");
        int x = Integer.parseInt(lastIsland.substring(0, lastIsland.indexOf(',')));
        int z = Integer.parseInt(lastIsland.substring(lastIsland.indexOf(',') + 1));

        if(x - 1 < 0) {
            x = z + 1;
            z = 0;
        } else {
            x--;
            z++;
        }

        main.getConfig().set("Data.last_island", x + "," + z);
        main.saveConfig();

        int delta = islandSize + deltaIsland;

        int centerX = baseX + delta * x;
        int centerZ = baseZ + delta * z;

        return new Region(centerX - 0.5 * islandSize,
                centerZ - 0.5 * islandSize,
                centerX + 0.5 * islandSize,
                centerZ + 0.5 * islandSize);
    }

    public static void saveRegion(Main main, Region region) {
        Database.setConnection(main);
        try {
            Statement stmt = main.con.createStatement();

            stmt.execute("INSERT INTO `regions` VALUES ('" +
                    region.getUuid() + "', '" +
                    region.getMinX() + "', '" +
                    region.getMaxX() + "', '" +
                    region.getMinZ() + "', '" +
                    region.getMaxZ() + "');");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Region determineRegion(Main main, Location loc) {
        Database.setConnection(main);

        try {
            Statement stmt = main.con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `regions` WHERE " +
                    "x_min <= '" + loc.getX() + "' AND " +
                    "x_max >= '" + loc.getX() + "' AND " +
                    "z_min <= '" + loc.getZ() + "' AND " +
                    "z_max >= '" + loc.getZ() + "';");
            if(rs.next()) {
                return new Region(rs.getString("id"),
                        rs.getInt("x_min"),
                        rs.getInt("z_min"),
                        rs.getInt("x_max"),
                        rs.getInt("z_max"));
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isInRegion(Region region, Location location) {
        return location.getX() >= region.getMinX() && location.getX() <= region.getMaxX() &&
                location.getZ() >= region.getMinZ() && location.getZ() <= region.getMaxZ();
    }
}
