package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveBackBlockCommand extends AdminParkourCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);

        if (!SenderHasPermission(sender, ParkourPlugin.permissionSet.configureParkourPermission)) return true;
        if(!isPlayerOnParkour(player)) return true;


        Parkour parkour = session.getParkour();

        for(String materialName : args){
            try{
                Material material = parkour.removeBackBlock(materialName);
                player.sendMessage("Removed backblock "+material.name());
            }
            catch(IllegalStateException exception){
                player.sendMessage(exception.getMessage());
            }
        }
        parkour.saveParkour(parkour.folderName + parkour.dataFileNameInsideFolder);
        return true;
    }
}
