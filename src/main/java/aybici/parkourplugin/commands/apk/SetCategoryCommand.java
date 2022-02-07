package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.ParkourCategory;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCategoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);

        switch (args[0]){
            case "easy":
                session.getParkour().setCategory(ParkourCategory.EASY);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Easy.");
                break;
            case "medium":
                session.getParkour().setCategory(ParkourCategory.MEDIUM);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Medium.");
                break;
            case "hard":
                session.getParkour().setCategory(ParkourCategory.HARD);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Hard.");
                break;
            case "dropper":
                session.getParkour().setCategory(ParkourCategory.DROPPER);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Dropper.");
                break;
            case "kz":
                session.getParkour().setCategory(ParkourCategory.KZ);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii KZ.");
                break;
            case "community":
                session.getParkour().setCategory(ParkourCategory.COMMUNITY);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Community.");
                break;
            case "special":
                session.getParkour().setCategory(ParkourCategory.SPECIAL);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Special.");
                break;
            case "no_category":
                session.getParkour().setCategory(ParkourCategory.NO_CATEGORY);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii no_category.");
                break;
            case "event":
                session.getParkour().setCategory(ParkourCategory.EVENT);
                player.sendMessage("Dodano parkour " + session.getParkour().getName() + " do kategorii Event.");
                break;
            default:
                player.sendMessage("Nie ma takiej kategorii!");
                return false;
        }
        player.sendMessage("Id w tej kategorii to: " + session.getParkour().getIdentifier());
        return true;
    }
}
