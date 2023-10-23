package aybici.parkourplugin.commands;

import aybici.parkourplugin.users.User;
import aybici.parkourplugin.users.UserManager;
import aybici.parkourplugin.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if(!player.isOp()){
            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNie masz odpowiednich uprawnień :)"));
            return true;
        }

        if(args.length == 1){
            Player argPlayer = Bukkit.getPlayer(args[0]);
            User argUser = UserManager.getUserByName(argPlayer.getName());

            if(argPlayer == null) return true;

            argUser.addKeys(1);
            argUser.saveUser();

            player.sendMessage(ChatUtil.fixColor("&b>&a> &bDodano klucz dla gracza &e" + argPlayer.getName()));
        }
        return false;
    }
}
