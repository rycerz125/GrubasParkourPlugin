package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CheckpointCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);
        session.checkpoint.setCheckpoint();
        return true;
    }
}