package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.sessions.ParkourSession;
import dev.alangomes.springspigot.context.Context;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name="cp")
public class CheckpointCommand implements Callable<String> {

    @Autowired
    private Context context;

    @Override
    public String call() {
        Player player = context.getPlayer();
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);
        session.checkpoint.setCheckpoint();
        return "LOL";
    }
}