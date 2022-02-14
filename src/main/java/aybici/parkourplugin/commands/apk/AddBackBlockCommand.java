package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddBackBlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        Parkour parkour = ParkourPlugin.parkourSessionSet.getSession(player).getParkour();

        if(parkour == null) {
            sender.sendMessage("You need to join parkour to use this command!");
            return false;
        }

        for(String materialName : args){
            try {
                Material material = parkour.addBackBlock(materialName);
                player.sendMessage("Added backblock "+material.name());
            }
            catch(IllegalStateException exception){
                player.sendMessage(exception.getMessage());
            }
        }
        parkour.saveParkour(parkour.folderName + parkour.dataFileNameInsideFolder);
        return true;
    }
}
