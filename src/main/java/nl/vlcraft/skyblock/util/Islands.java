package nl.vlcraft.skyblock.util;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.database.Database;
import nl.vlcraft.skyblock.guicallback.SlotItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Islands {

    public static List<String> getIslandPlayers(Main main, String islandId) {
        throw new UnsupportedOperationException();
    }

    public static String getIslandOwner(Main main, String islandId) {
        throw new UnsupportedOperationException();
    }

    public static List<String> getIslandMods(Main main, String islandId) {
        throw new UnsupportedOperationException();
    }

    public static List<String> getIslandMembers(Main main, String islandId) {
        throw new UnsupportedOperationException();
    }

    public static Location getIslandHome(Main main, String islandId) {
        Database.setConnection(main);
        try {
            Statement stmt = main.con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT home FROM `islands` WHERE id='" + islandId + "';");
            if(rs.next()) {
                return SerializationManager.stringToLocation(rs.getString("home"));
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<SlotItem> getOwnedIslands(Main main, String playerUuid, int startSlot) {
        Database.setConnection(main);
        List<SlotItem> slotItems = new ArrayList<>();
        try {
            Statement stmt = main.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id " +
                    "FROM islands " +
                    "WHERE owner='" + playerUuid + "';");

            int i = startSlot;
            while(rs.next()) {
                slotItems.add(new SlotItem(
                        i++,
                        ItemStackMethods.getPlayerHead(Bukkit.getOfflinePlayer(UUID.fromString(playerUuid))),
                        rs.getString("id")));
            }

            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return slotItems;
    }

    public static List<SlotItem> getTrustedIslands(Main main, String playerUuid, int startSlot) {
        Database.setConnection(main);
        List<SlotItem> slotItems = new ArrayList<>();
        try {
            Statement stmt = main.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, owner FROM islands " +
                    "WHERE moderators LIKE '%" + playerUuid + "%' " +
                    "OR members LIKE '%" + playerUuid + "%';");

            int i = startSlot;
            while(rs.next()) {
                slotItems.add(new SlotItem(
                        i++,
                        ItemStackMethods.getPlayerHead(Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("owner")))),
                        rs.getString("id")));
            }

            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return slotItems;
    }

    public static int getAmountOfAvailableIslands(Main main, String playerUuid) {
        Database.setConnection(main);
        int result = 0;
        try {
            Statement stmt = main.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS 'amount' " +
                    "FROM `islands` " +
                    "WHERE owner='" + playerUuid + "' " +
                    "OR moderators LIKE '%" + playerUuid + "%' " +
                    "OR members LIKE '%" + playerUuid + "%';");

            if(rs.next()) {
                result = rs.getInt("amount");
            }
            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
