package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GlobalDeleteTopsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        if (true){ // TODO: Really nigga?
            sender.sendMessage("nie ma opcji");
            return false;
        }
        if (args.length == 1){
            int removes = 0;
            for (Parkour parkour : ParkourPlugin.parkourSet.getParkours()){
                removes += parkour.getTopListObject().removeAllTimesOfPlayer(Bukkit.getOfflinePlayer(args[0]));
                parkour.getTopListObject().saveTopList();
            }
            sender.sendMessage("Usunięto czasy gracza " + Bukkit.getOfflinePlayer(args[0]).getName() + ": " + removes);
            if (removes != 0) return true;
            if (args[0].equals("all")){
                removes = 0;
                for (Parkour parkour : ParkourPlugin.parkourSet.getParkours()){
                    removes += parkour.getTopListObject().getTopList().size();
                    parkour.getTopListObject().clearTopList(parkour);
                }
                sender.sendMessage("Usunięto wszystkie czasy na wszystkich parkourach: " + removes);
                return true;
            }
            sender.sendMessage("Nie znaleziono żadnych czasów tego gracza: " + Bukkit.getOfflinePlayer(args[0]).getName());
            return true;
        }
        sender.sendMessage("Niepoprawna ilość argumentów!");
        return false;
    }
}
