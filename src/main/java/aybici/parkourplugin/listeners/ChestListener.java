package aybici.parkourplugin.listeners;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.chests.ChestManager;
import aybici.parkourplugin.users.User;
import aybici.parkourplugin.users.UserManager;
import aybici.parkourplugin.utils.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class ChestListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Action action = event.getAction();

        if(action != Action.RIGHT_CLICK_BLOCK)
            return;

        Material blockType = event.getClickedBlock().getType();

        if(blockType != Material.CHEST)
            return;

        int x = event.getClickedBlock().getX();
        int y = event.getClickedBlock().getY();
        int z= event.getClickedBlock().getZ();

        if(!ParkourPlugin.getInstance().chestLocationConfig.getBoolean("chests." + x + "," + y + "," + z))
            return;

        Player player = event.getPlayer();
        User user = UserManager.getUserByName(player.getName());

        if(user.getKeys() < 1){
            player.sendMessage(ChatUtil.fixColor("&b>&a> &cNie posiadasz kluczy!"));
            event.setCancelled(true);
            return;
        }

        user.removeKeys(1);
        user.saveUser();

        event.setCancelled(true);

        int chance = new Random().nextInt(15);

        Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botwiera skrzynię!"));

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fixColor("&aOtwieranie&b")));
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f);

        ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fixColor("&aOtwieranie&b.")));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

            ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fixColor("&aOtwieranie&b.")));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fixColor("&aOtwieranie&b.")));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                    ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {

                        switch(chance){
                            case 0:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a100 XP"));
                                user.addExp(100);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a100 XP"));
                                break;

                            case 1:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a200 XP"));
                                user.addExp(200);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a200 XP"));
                                break;

                            case 2:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a300 XP"));
                                user.addExp(300);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a300 XP"));
                                break;

                            case 3:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a400 XP"));
                                user.addExp(400);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a400 XP"));
                                break;

                            case 4:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a500 XP"));
                                user.addExp(500);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a500 XP"));
                                break;

                            case 5:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a600 XP"));
                                user.addExp(600);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a600 XP"));
                                break;

                            case 6:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a700 XP"));
                                user.addExp(700);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a700 XP"));
                                break;

                            case 7:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a800 XP"));
                                user.addExp(800);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a800 XP"));
                                break;

                            case 8:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a900 XP"));
                                user.addExp(900);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a900 XP"));
                                break;

                            case 9:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1000 XP"));
                                user.addExp(1000);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a1000 XP"));
                                break;

                            case 10:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1600 XP"));
                                user.addExp(1600);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a1600 XP"));
                                break;

                            case 11:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1700 XP"));
                                user.addExp(1700);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a1700 XP"));
                                break;

                            case 12:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1800 XP"));
                                user.addExp(1800);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a1800 XP"));
                                break;

                            case 13:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1900 XP"));
                                user.addExp(1900);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a1900 XP"));
                                break;

                            case 14:
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a2000 XP"));
                                user.addExp(2000);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                                Bukkit.broadcastMessage(ChatUtil.fixColor("&b&a> &bGracz &a" + player.getName() + " &botrzymał: &a2000 XP"));
                                break;
                        }
                    }, 20L);
                }, 20L);
            }, 20L);
        }, 20L);
    }
}
