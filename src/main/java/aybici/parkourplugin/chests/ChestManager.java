package aybici.parkourplugin.chests;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.users.User;
import aybici.parkourplugin.users.UserManager;
import aybici.parkourplugin.utils.ChatUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class ChestManager {

    public static void openChest(Player player){
        User user = UserManager.getUserByName(player.getName());

        int chance = new Random().nextInt(100);
        Random randomBadExp = new Random();
        Random randomRareExp = new Random();
        Random randomLegendExp = new Random();

        int badExp = randomBadExp.nextInt(4);
        int legendExp = randomLegendExp.nextInt(13);

        player.sendTitle("", ChatUtil.fixColor("&aOtwieranie&b"), 10, 20, 10);
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f);

        ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
            player.sendTitle("", ChatUtil.fixColor("&aOtwieranie&b."), 10, 20, 10);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

            ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
                player.sendTitle("", ChatUtil.fixColor("&aOtwieranie&b.."), 10, 20, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {
                    player.sendTitle("", ChatUtil.fixColor("&aOtwieranie&b..."), 10, 20, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                    ParkourPlugin.getInstance().getServer().getScheduler().runTaskLater(ParkourPlugin.getInstance(), () -> {

                        if(chance < 99) {
                            if(badExp == 0){
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a100 XP"));
                                user.addExp(100);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                            }
                            if(badExp == 1){
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a200 XP"));
                                user.addExp(200);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                            }
                            if(badExp == 2){
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a300 XP"));
                                user.addExp(300);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                            }
                            if(badExp == 3){
                                player.sendTitle("", ChatUtil.fixColor("&bNagroda: &7zwykła"), 30, 30, 30);
                                player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a400 XP"));
                                user.addExp(400);
                                user.saveUser();
                                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                            }
                        }

                        if(chance < 30){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a500 XP"));
                            user.addExp(500);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 29){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a600 XP"));
                            user.addExp(600);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 28){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a700 XP"));
                            user.addExp(700);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 27){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a800 XP"));
                            user.addExp(800);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 26){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a900 XP"));
                            user.addExp(900);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 25){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &9rzadka"), 30, 30, 30);
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1000 XP"));
                            user.addExp(1000);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 5){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1600 XP"));
                            user.addExp(1600);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 4){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1700 XP"));
                            user.addExp(1700);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 3){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1800 XP"));
                            user.addExp(1800);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 2){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a1900 XP"));
                            user.addExp(1900);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }

                        if(chance < 1){
                            player.sendTitle("", ChatUtil.fixColor("&bNagroda: &6legendarna"));
                            player.sendMessage(ChatUtil.fixColor("&b>&a> &aNagroda: &a2000 XP"));
                            user.addExp(2000);
                            user.saveUser();
                            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                        }
                    }, 20L);
                }, 20L);
            }, 20L);
        }, 20L);
    }

    public static void addKeyAfterPlayerFinishedParkour(Player player, int keys){
        User user = UserManager.getUserByName(player.getName());
        if(player == null) return;
        user.addKeys(1);
        user.saveUser();
        player.sendMessage(ChatUtil.fixColor("&bZdobyłeś klucz!"));

        Firework f = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect
                .builder()
                .flicker(true)
                .trail(false)
                .with(FireworkEffect.Type.STAR)
                .withColor(Color.AQUA)
                .withFade(Color.GREEN)
                .build());
        fm.setPower(0);
        f.setFireworkMeta(fm);
        f.detonate();
    }
}

// 100-400 - 65%
// 500-1000 - 30%
// 1100-2000 - 5%