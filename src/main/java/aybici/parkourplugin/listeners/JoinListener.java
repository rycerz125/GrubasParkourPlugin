package aybici.parkourplugin.listeners;

import aybici.parkourplugin.itembuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore())
            setItems(player);
        if(player.hasPlayedBefore())
            setItems(player);
    }

    public static void setItems(Player player){
        final ItemStack book = new ItemBuilder(Material.BOOK, 1).setName("§bMenu Parkourow - /pk kategoria numer").addLoreLine("developing").toItemStack();
        final ItemStack blazeRod = new ItemBuilder(Material.BLAZE_ROD, 1).setName("§bPoprzedni/Następny parkour").addLoreLine("left/right click").toItemStack();
        player.getInventory().setItem(3, book);
        player.getInventory().setItem(2, blazeRod);
        player.updateInventory();
    }
}
