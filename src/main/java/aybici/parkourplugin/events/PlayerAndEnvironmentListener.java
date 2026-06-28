package aybici.parkourplugin.events;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.users.User;
import aybici.parkourplugin.utils.TabUtil;
import com.earth2me.essentials.Essentials;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class PlayerAndEnvironmentListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (player.getName().startsWith("McDown")) {
            player.kickPlayer("");
            return;
        }

        if (!player.hasPlayedBefore()){
            ParkourPlugin.uuidList.savePlayerUUIDAndNameInFile(player);
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(true); // set player undamagable
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.itemsPermission))
            player.setCanPickupItems(false);

        //gdyby ktoś używał silnika, w którym nie da się wylączyć kolizji w configu (Bukkit, Spigot) - odkomentować
        //Paper ma tę funkcję
//        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"team add nocoll");     //off collisions
//        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"team modify nocoll collisionRule never");
//        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"team join nocoll " + player.getName());
        if (!ParkourPlugin.uuidList.contains(player.getUniqueId())) {
            ParkourPlugin.uuidList.addIdentifier(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD + "Witamy po raz pierwszy na serwerze GrubasKraft!");
            player.sendMessage(ChatColor.GOLD + "Jesteś naszym " + ParkourPlugin.uuidList.getLength() + ". graczem!");
            Bukkit.getLogger().info("Dodano nowego gracza nr " + ParkourPlugin.uuidList.getLength() + " o nicku: " + player.getName());
        }
        else player.sendMessage(ChatColor.GOLD + "Witamy na serwerze GrubasKraft Dev!");

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        Location lobby = ParkourPlugin.lobby.getLobbyLocation();

        if (lobby != null) {
            player.teleport(lobby);
        }

        ParkourPlugin.parkourSessionSet.deleteParkourSession(player);
        TabUtil.refreshAllPlayersTab();
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.itemsPermission))
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event){
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.buildPermission))
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInPortal(PlayerPortalEvent event){
        if (!event.getPlayer().isOp())
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event){
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.buildPermission))
            event.setCancelled(true);
    }
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onExplosion(EntityExplodeEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event){
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD) || event.getCause().equals(BlockIgniteEvent.IgniteCause.LAVA))
            event.setCancelled(true);
    }
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onBlockFade(BlockFadeEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onFormEvent(BlockFormEvent event){ //snieg tworzony, zamarzanie
        event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){ //farmland breaking
        if (e.getClickedBlock() != null && e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND)
        e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){//????
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.buildPermission)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){//?????
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.buildPermission)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){// item frames ale tylko niepuste
        if (!event.getDamager().hasPermission(ParkourPlugin.permissionSet.buildPermission)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemFrameDamage(HangingBreakByEntityEvent event){
        if (!event.getRemover().hasPermission(ParkourPlugin.permissionSet.buildPermission)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMsgMuted(PlayerCommandPreprocessEvent event){
        String command = event.getMessage();
        Player player = event.getPlayer();
        boolean muted = false;
        if(ParkourPlugin.getInstance().essentials != null)
            muted = ParkourPlugin.getInstance().essentials.getUser(player).isMuted();
        if (command.contains("msg ") && muted){
            player.sendMessage("Nie możesz rozpoczynać nowych dyskusji prywatnych z powodu mute.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void ongamemodechange(PlayerGameModeChangeEvent event){
        if (!event.getPlayer().hasPermission(ParkourPlugin.permissionSet.buildPermission)){
            event.setCancelled(true);
        }
    }
//    @EventHandler
//    public void onGenerateChunk(ChunkLoadEvent event){
//        event.getChunk().
//    }

//    @EventHandler
//    public void onUnloadChunk(ChunkUnloadEvent event){
//        //if(event.getChunk().getInhabitedTime() == Long.MAX_VALUE - 125)
//            event.setSaveChunk(false);
//            event.getChunk().unload(false);
//    }
}



