package aybici.parkourplugin;

import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.sessions.PlayerTimer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionSaver implements Listener {
    public HashMap<Player, List<LocationWithTime> > playerDemosHashMap = new HashMap<>();
    private HashMap<Player, Boolean> doSaving = new HashMap<>();
    private BukkitTask playTask;
    private HashMap<Player, Boolean> playerWatchingHash = new HashMap<>();
    public boolean isPlayerWatching(Player player){
        if (!playerWatchingHash.containsKey(player)) return false;
        else return (playerWatchingHash.get(player));
    }
    public void setPlayerWatching(Player player, boolean value){
        playerWatchingHash.remove(player);
        playerWatchingHash.put(player,value);
    }
    public void start(Player player){
        playerDemosHashMap.remove(player);
        doSaving.remove(player);
        playerDemosHashMap.put(player, new ArrayList<>());
        doSaving.put(player, true);
    }
    public void stop(Player player){
        if (!doSaving.containsKey(player)) return; //nie powinno wystapic
        doSaving.replace(player, false);
    }
    public void saveToFile(Player player, String directory){
        String properDirectory = directory  + File.separator + "demos"+File.separator +player.getName() + ".txt";
        FileCreator.createFile(properDirectory);
        try {
            FileWriter fileWriter = new FileWriter(properDirectory);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(player.getName() + "," + player.getLocation().getWorld().getName()+ "\n");
            for (int i = 0; i < playerDemosHashMap.get(player).size(); i++){
                Location location = playerDemosHashMap.get(player).get(i).location;
                writer.write(location.getX() + "," + location.getY() + "," + location.getZ() + ","
                                + location.getPitch() + "," + location.getYaw() + "," +playerDemosHashMap.get(player).get(i).time+ "\n");
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage(ChatColor.YELLOW + "Zapisano najlepsze przej??cie na mapie! aby zobaczy?? replay wpisz /playdemo " + player.getName() + " :)");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (!doSaving.containsKey(player)) return;
        if (doSaving.get(player)){
            playerDemosHashMap.get(player).add(new LocationWithTime(System.currentTimeMillis(),player.getLocation()));
        }
    }
    public void playDemo(Player player, List<LocationWithTime> locationList, int slowMotion){
        Parkour parkour = ParkourPlugin.parkourSessionSet.getSession(player).getParkour();
        if (locationList.size() > 0)
            ParkourPlugin.parkourSessionSet.deleteParkourSession(player);
        locationList = interpolateList(locationList, slowMotion);

        //Entity entity = locationList.get(0).location.getWorld().spawnEntity(locationList.get(0).location, EntityType.ZOMBIE);
        //player.setGameMode(GameMode.SPECTATOR);
        //player.setSpectatorTarget(entity);
        setPlayerWatching(player,true);
        PlayerTimer playerTimer = new PlayerTimer(player,slowMotion);
        playerTimer.startTimer();
        List<LocationWithTime> finalLocationList = locationList;
        playTask = new BukkitRunnable(){

            int i = 0;
            @Override
            public void run() {
                if (i < finalLocationList.size() && isPlayerWatching(player)) {
                    player.teleport(finalLocationList.get(i).location);
                    i++;
                } else {
                    if (parkour != null)
                        ParkourPlugin.parkourSessionSet.teleportToParkour(player, parkour.getName());
                    else ParkourPlugin.lobby.teleportPlayerToLobby(player);
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"skin clear " + player.getName());
                    cancel();
                    setPlayerWatching(player,false);
                    playerTimer.resetTimer();
                }
            }
        }.runTaskTimer(ParkourPlugin.getInstance(), 0, 1);
    }
    public List<LocationWithTime> interpolateList(List<LocationWithTime> locationList, int slowMotion){
        List<LocationWithTime> interpolatedList = new ArrayList<>();
        for (int i = 0; i < locationList.size() - 1; i++){
            for (int j = 0; j < slowMotion; j++){
                interpolatedList.add(getInterpolatedLocation(locationList.get(i), locationList.get(i+1), slowMotion, j));
            }
        }
        interpolatedList.add(locationList.get(locationList.size() - 1));

        return interpolatedList;
    }
    private LocationWithTime getInterpolatedLocation(LocationWithTime location1, LocationWithTime location2, int slowMotion, int element){
        double x = location1.location.getX() + (location2.location.getX() - location1.location.getX())*element/slowMotion;
        double y = location1.location.getY() + (location2.location.getY() - location1.location.getY())*element/slowMotion;
        double z = location1.location.getZ() + (location2.location.getZ() - location1.location.getZ())*element/slowMotion;
        float pitch = location1.location.getPitch() + (location2.location.getPitch() - location1.location.getPitch())*element/slowMotion;
        float yaw = location1.location.getYaw() + (location2.location.getYaw() - location1.location.getYaw())*element/slowMotion;
        World world = location1.location.getWorld();
        long time = location1.time + (location2.time - location1.time)*element/slowMotion;
        Location location = new Location(world,x,y,z,yaw,pitch);
        LocationWithTime interpolatedLocation = new LocationWithTime(time,location);
        return interpolatedLocation;
    }
    public List<LocationWithTime> getDemo(OfflinePlayer player, Parkour parkour){
        List<LocationWithTime> locationList = null;
        try {
            locationList =  loadDemo(parkour.folderName + File.separator + "demos"+File.separator + player.getName() + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationList;
    }
    private List<LocationWithTime> loadDemo(String directory) throws IOException {
        List<LocationWithTime> locationList = new ArrayList<>();
        String currentLine;
        BufferedReader reader = new BufferedReader(new FileReader(directory));
        World world = null;

        boolean lineExists = true;
        while (lineExists) {
            currentLine = reader.readLine();
            if (currentLine == null) lineExists = false;
            else {
                List<String> convertedLine = Stream.of(currentLine.split(",", -1))
                        .collect(Collectors.toList());
                if (convertedLine.size() == 6) {
                    Location location = new Location(world, Double.parseDouble(convertedLine.get(0)),
                            Double.parseDouble(convertedLine.get(1)),Double.parseDouble(convertedLine.get(2)),
                            Float.parseFloat(convertedLine.get(4)),Float.parseFloat(convertedLine.get(3)));
                    Long time = Long.parseLong(convertedLine.get(5));
                    LocationWithTime locationWithTime = new LocationWithTime(time, location);
                    locationList.add(locationWithTime);
                }
                else world = Bukkit.getWorld(convertedLine.get(convertedLine.size()-1));
            }
        }
        reader.close();
        return sortLocationList(locationList);
    }

    private class LocationWithTime{
        public Location location;
        public Long time;
        public LocationWithTime(Long time, Location location){
            this.location = location;
            this.time = time;
        }
    }
    private List<LocationWithTime> sortLocationList(List<LocationWithTime> locationList){
        locationList.sort(Comparator.comparing(o -> o.time));
        return locationList;
    }
}
