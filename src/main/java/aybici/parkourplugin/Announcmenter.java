package aybici.parkourplugin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Announcmenter {

    private static long mins = 11; //Wysyłaj wiadomość co 11 minut

    private static List<TextComponent> messages = new ArrayList<>();

    private static int current = 0;

    public static void run(ParkourPlugin plugin){
        TextComponent message1 = new TextComponent(ChatColor.AQUA+ ">"+ ChatColor.GREEN + "> "+ ChatColor.WHITE + "Zakup usługę "+ ChatColor.YELLOW + "VipMan "+ ChatColor.WHITE + "na naszym sklepie już teraz! Wszystkie dodatkowe przywileje możesz sprawdzić na naszej stronie: "+ ChatColor.YELLOW + ChatColor.UNDERLINE + "http://itemshop.pl/842866.html");
        message1.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://itemshop.pl/842866.html" ) );

        TextComponent message2 = new TextComponent(ChatColor.AQUA+ ">"+ ChatColor.GREEN + "> "+ ChatColor.WHITE + "Wejdź na naszego "+ ChatColor.BLUE + "discorda"+ ChatColor.WHITE + ", aby być na bieżąco - "+ ChatColor.BLUE + ChatColor.UNDERLINE + "https://discord.gg/AAAkcX3zae");
        message2.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://discord.gg/AAAkcX3zae" ) );

        messages.add(message1);
        messages.add(message2);

        Bukkit.getScheduler().runTaskTimer(plugin, ()->{
            Bukkit.spigot().broadcast(messages.get(current));
            current++;
            if(current >= messages.size()) current = 0;
        }, 0L, mins * 20 * 60);
    }
}
