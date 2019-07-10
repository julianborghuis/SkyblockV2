package nl.vlcraft.skyblock.commands;

import nl.vlcraft.skyblock.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkyblockCommand implements CommandExecutor {

    private Main main;

    public SkyblockCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        return false;
    }
}
