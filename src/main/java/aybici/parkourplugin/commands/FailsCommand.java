package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.fails.FailSet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class FailsCommand extends OnParkourCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!isPlayerOnParkour(player)) return true;
        Parkour parkour = ParkourPlugin.parkourSessionSet.getSession(player).getParkour();
        if (parkour.getFailSetObject() == null) {
            FailSet failSet = new FailSet(parkour);
            try {
                failSet.loadFailSet(parkour.folderName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(ChatColor.AQUA + ">" + ChatColor.GREEN+ "> " + "Trwa ładowanie Twoich faili lub nikt jeszcze nie spadł na tym parkourze..." + ChatColor.GREEN + " Spróbuj ponownie lub spadnij!");
            return true;
        }
        player.sendMessage(ChatColor.AQUA + ">" + ChatColor.GREEN+ "> " + "Ilość twoich faili na tym parkourze to: " + ChatColor.AQUA + parkour.getFailSetObject().getNumberOfPlayerFails(player));
        return true;
    }
}
