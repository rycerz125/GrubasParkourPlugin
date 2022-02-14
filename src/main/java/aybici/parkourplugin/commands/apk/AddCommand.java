package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        try {
            ParkourPlugin.parkourSet.addParkour(args[0], player.getLocation());
            player.sendMessage("Parkour with name \""+args[0]+"\" added!");
            ParkourPlugin.parkourSessionSet.teleportToParkour(player, args[0]);
        }
        catch(Exception exception){
            player.sendMessage(exception.getMessage());
        }

        return true;
    }
}
