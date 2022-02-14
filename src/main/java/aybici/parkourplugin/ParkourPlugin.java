package aybici.parkourplugin;

import aybici.parkourplugin.blockabovereader.UnderPlayerBlockWatcher;
import aybici.parkourplugin.commands.CommandExecutorSetter;
import aybici.parkourplugin.events.PlayerAndEnvironmentListener;
import aybici.parkourplugin.listeners.InteractListener;
import aybici.parkourplugin.listeners.JoinListener;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.ParkourSet;
import aybici.parkourplugin.parkours.TopLine;
import aybici.parkourplugin.parkours.TopListDisplay;
import aybici.parkourplugin.sessions.ParkourSessionSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParkourPlugin extends JavaPlugin {
    public static final ParkourSet parkourSet = new ParkourSet();
    public static ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);
    public static final UnderPlayerBlockWatcher underPlayerBlockWatcher = new UnderPlayerBlockWatcher();
    public static PositionSaver positionSaver = new PositionSaver();
    private static ParkourPlugin plugin;
    public static ParkourPlugin getInstance(){
        return plugin;
    }
    public static TopListDisplay topListDisplay = new TopListDisplay();
    public static UUIDList uuidList = new UUIDList();
    public static PermissionSet permissionSet = new PermissionSet();
    public static Lobby lobby = new Lobby();

    @Override
    public void onEnable() {
        plugin = this;
        uuidList.loadList();
        uuidList.loadPlayerNames();
        parkourSet.loadParkours(parkourSet.parkoursFolder);
        lobby.loadLobbyLocation(lobby.directory);
        lobby.runTeleportToLobbyAllTask();
        CommandExecutorSetter.setExecutors(this);
        Bukkit.getServer().getPluginManager().registerEvents(underPlayerBlockWatcher, this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerAndEnvironmentListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(positionSaver, this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InteractListener(), this);
        //saveBinEinTimes();
    }


    public ParkourPlugin()
    {
        super();
    }

    protected ParkourPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
    private void saveBinEinTimes() {
        List<String> gracze = new ArrayList<>();
        gracze.add("BinEin");
        gracze.add("BinEinMc");
        gracze.add("BinEinPk");
        int liczba = 0;
        for (String gracz : gracze) {
            for (Parkour parkour : parkourSet.getParkours()) {
                List<TopLine> topki = topListDisplay.getAllTimesOfPlayer(Bukkit.getOfflinePlayer(gracz), parkour.getTopListObject().getTopList());
                if (topki.size() != 0) {
                    liczba += topki.size();
                    Bukkit.getLogger().info("Znaleziono " + topki.size() + "topek na " + parkour.getName() + " gracza " + gracz  +" - "+ liczba);
                    for (TopLine topLine : topki) {
                        topLine.saveTopLineString("dataBase1" + File.separator + "parkours" + File.separator + "parkourMap_" +
                                parkour.getName() + File.separator + "topList.txt");
                    }
                }
            }
        }
    }
}
