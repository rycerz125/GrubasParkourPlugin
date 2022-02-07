package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import dev.alangomes.springspigot.context.Context;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name="setlobby")
public class SetLobbyCommand implements Runnable  {

    @Autowired
    private Context context;

    @Override
    public void run() {
        Player player = context.getPlayer();
        if (!player.hasPermission(ParkourPlugin.permissionSet.setLobbyPermission)){
            player.sendMessage(ChatColor.RED + "Nie masz permisji, żeby ustawić lobby!");
            return;
        }
        ParkourPlugin.lobby.setLobbyLocation(player.getLocation());
        player.sendMessage("Ustawiono lokalizację lobby na: " + ParkourPlugin.lobby.getLobbyLocation().toString());
    }
}