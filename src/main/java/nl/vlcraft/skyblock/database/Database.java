package nl.vlcraft.skyblock.database;

import nl.vlcraft.skyblock.Main;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static void setConnection(Main main) {
        try {
            if(main.con != null) {
                if(main.con.isValid(1000)) return;
            }
            main.con = DriverManager.getConnection("jdbc:mysql://" + main.host + ":" + main.port + "/" + main.db + "?useSSL=false", main.username, main.password);
        } catch (SQLException e) {
            System.out.println("Skyblock > Error: Failed to setup Database connection.");
        }
    }
}
