package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.TagsFacade;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddTagToParkourCommand extends AdminParkourCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if (!SenderHasPermission(sender, ParkourPlugin.permissionSet.configureParkourPermission)) return true;
        if (!isPlayerOnParkour(player)){
            player.sendMessage("Nie jesteś na żadnym parkourze.");
            return true;
        }

        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);
        Parkour parkour = session.getParkour();

        String tag = args[0];

        boolean good = TagsFacade.addTagToParkour(parkour, tag);

        if(good)
            player.sendMessage("Dodano nowy tag do mapy");
        else player.sendMessage("Nie udało się dodać tagu! Sprawdź nazwę tagu.");

        return true;
    }
}
