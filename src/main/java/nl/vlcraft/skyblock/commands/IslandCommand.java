package nl.vlcraft.skyblock.commands;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.util.InteractionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCommand implements CommandExecutor {

    private Main main;

    public IslandCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(main.prefix + "Je moet een speler zijn om deze command uit te voeren.");
            return false;
        }

        Player player = (Player) sender;
        InteractionManager.openIslandSelectionMenu(main, player);

        return true;
    }
}
