package aybici.parkourplugin.commands;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "heal")
public class HealCommand implements Runnable {

    @Autowired
    private Context context;

    @Override
    public void run() {
        Player player = context.getPlayer();
        player.sendMessage("KSDHJFKL");
        player.setHealth(20);
    }
}