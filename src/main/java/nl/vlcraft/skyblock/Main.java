package nl.vlcraft.skyblock;

import nl.vlcraft.skyblock.commands.IslandCommand;
import nl.vlcraft.skyblock.commands.SkyblockCommand;
import nl.vlcraft.skyblock.events.RegionEvents;
import nl.vlcraft.skyblock.objects.Region;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public String prefix = "§3Skyblock §f> §7";

    public Map<Player, Region> playerRegion = new HashMap<>();

    // Database
    public String username;
    public String password;
    public String host;
    public String db;
    public String port;
    public Connection con;

    @Override
    public void onEnable() {
        // Commands
        getCommand("island").setExecutor(new IslandCommand(this));
        getCommand("skyblock").setExecutor(new SkyblockCommand(this));

        // Events
        Bukkit.getPluginManager().registerEvents(new RegionEvents(this), this);

        // Database
        getConfig().options().copyDefaults(true);
        saveConfig();
        registerDatabase();
    }

    private void registerDatabase() {
        username = getConfig().getString("Settings.Username");
        password = getConfig().getString("Settings.Password");
        host = getConfig().getString("Settings.Host");
        db = getConfig().getString("Settings.DB");
        port = getConfig().getString("Settings.Port");
    }
}
