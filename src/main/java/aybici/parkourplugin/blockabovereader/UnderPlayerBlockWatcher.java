package aybici.parkourplugin.blockabovereader;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UnderPlayerBlockWatcher implements Listener {
    public final HashMap<UUID, List<OnNewBlockPlayerStandObserver>> underPlayerBlockObservers = new HashMap<>();

    public void registerNewObserver(Player player, OnNewBlockPlayerStandObserver observer){
        getPlayerObservers(player).add(observer);
    }

    private List<OnNewBlockPlayerStandObserver> getPlayerObservers(Player player){
        if(underPlayerBlockObservers.containsKey(player.getUniqueId()))
            return underPlayerBlockObservers.get(player.getUniqueId());
        else{
            List<OnNewBlockPlayerStandObserver> newList = new ArrayList<>();
            underPlayerBlockObservers.put(player.getUniqueId(), newList);
            return newList;
        }
    }

    @EventHandler
    public void onAnyPlayerMove(PlayerMoveEvent event){
        Location location = event.getTo();
        Location locationUnder = location.clone().add(0,-0.6,0);
        int deltaX = 0;
        int deltaZ = 0;

        if (((int)(location.getX() - 0.3)) < ((int)location.getX())) deltaX = -1;
        else if(((int)(location.getX() + 0.3)) > ((int)location.getX())) deltaX = 1;

        if (((int)(location.getZ() - 0.3)) < ((int)location.getZ())) deltaZ = -1;
        else if(((int)(location.getZ() + 0.3)) > ((int)location.getZ())) deltaZ = 1;

        List<Block> blockList = new ArrayList<>();

        if (locationUnder.getBlock().isPassable()) { //---------
            if (deltaZ != 0) /// 1
                blockList.add(locationUnder.clone().add(0, 0, deltaZ).getBlock());
            if (deltaX != 0) /// 2
                blockList.add(locationUnder.clone().add(deltaX, 0, 0).getBlock());
            if (deltaX != 0 && deltaZ != 0) /// 3
                blockList.add(locationUnder.clone().add(deltaX, 0, deltaZ).getBlock());
        }
        blockList.add(locationUnder.getBlock()); //---------
        
        for(OnNewBlockPlayerStandObserver observer : getPlayerObservers(event.getPlayer())){
            observer.playerStandOnNewBlock(blockList);
        }
    }
}
