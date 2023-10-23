package aybici.parkourplugin.commands.holo;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourTopsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("parkour.holograms.setting"))
            return true;
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("1")){
                Player player = (Player) sender;
                Location whereToPut = player.getLocation().add(0.0, 1.0, 0.0);
                player.sendMessage(ChatUtil.fixColor("&b>&a> &bUstawiono hologram"));
                ParkourPlugin.getInstance().getConfig().set("hologramExp", locationToString(whereToPut));
                ParkourPlugin.getInstance().saveConfig();
            } else if(args[0].equalsIgnoreCase("2")){
                Player player = (Player) sender;
                Location whereToPut = player.getLocation().add(0.0, 1.0, 0.0);
                player.sendMessage(ChatUtil.fixColor("&b>&a> &bUstawiono hologram"));
                ParkourPlugin.getInstance().getConfig().set("hologramLevel", locationToString(whereToPut));
                ParkourPlugin.getInstance().saveConfig();
            } else if(args[0].equalsIgnoreCase("3")){
                Player player = (Player) sender;
                Location whereToPut = player.getLocation().add(0.0, 1.0, 0.0);
                player.sendMessage(ChatUtil.fixColor("&b>&a> &bUstawiono hologram"));
                ParkourPlugin.getInstance().getConfig().set("hologramWorldRecords", locationToString(whereToPut));
                ParkourPlugin.getInstance().saveConfig();
            } else {
                sender.sendMessage(ChatUtil.fixColor("&b>&a> &bPoprawne użycie: &a/hologram &b(&a1&b/&a2&b/&a3&b)"));
                return true;
            }
        } else if(args.length > 1) {
            sender.sendMessage(ChatUtil.fixColor("&b>&a> &bPoprawne użycie: &a/hologram &b(&a1&b/&a2&b/&a3&b)"));
            return true;
        }
        return false;
    }

    public static String locationToString(Location location){
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location stringToLocation(String s){
        String[] split = s.split(";");
        return new Location(Bukkit.getWorld(split[0]), Double.valueOf(split[1]), Double.valueOf(split[2]), Double.valueOf(split[3]), Float.valueOf(split[4]), Float.valueOf(split[5]));
    }
}
