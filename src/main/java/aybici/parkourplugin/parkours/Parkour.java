package aybici.parkourplugin.parkours;

import aybici.parkourplugin.FileCreator;
import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.fails.FailSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getLogger;

public class Parkour{
    private String name;
    private Location location;
    private Set<Material> backBlocks = new HashSet<>();
    private TopList topList = new TopList(this);
    public String dataFileNameInsideFolder;
    public String folderName;
    private int identifier;
    private ParkourCategory category;
    private FailSet failSet;
    private String description = "No description.";
    private List<Location> checkpoints = new ArrayList<>();

    Parkour(String name, Location location){
        this.name = name;
        this.location = location.clone();
        this.dataFileNameInsideFolder = File.separator + "parkourData.txt";
        this.folderName = ParkourPlugin.parkourSet.parkoursFolder + File.separator + "parkourMap_" + getName();
        this.category = ParkourCategory.NO_CATEGORY;
        this.identifier = 0;
        this.identifier = generateID();
    }
    Parkour(String name){
        this.name = name;
        this.dataFileNameInsideFolder = File.separator + "parkourData.txt";
        this.folderName = ParkourPlugin.parkourSet.parkoursFolder +File.separator +  "parkourMap_" + getName();
        this.category = ParkourCategory.NO_CATEGORY;
    }

    public String getDescription(){
        return description;
    }
    public FailSet getFailSetObject(){
        return failSet;
    }
    public void setFailSet(FailSet failSet){
        this.failSet = failSet;
    }
    public int getIdentifier(){
        return this.identifier;
    }
    public void setIdentifier(int identifier){
        this.identifier = identifier;
        saveParkour(folderName + dataFileNameInsideFolder);
    }
    public ParkourCategory getCategory(){
        return category;
    }

    public void setName(String name){
        File file = new File(folderName);
        this.name = name;
        this.folderName = ParkourPlugin.parkourSet.parkoursFolder + File.separator + "parkourMap_" + getName();
        file.renameTo(new File(folderName));
        saveParkour(folderName + dataFileNameInsideFolder);
    }
    public void setCategory(ParkourCategory category){
        this.category = category;
        this.identifier = 0;
        this.identifier = generateID();
        saveParkour(folderName + dataFileNameInsideFolder);
    }

    public TopList getTopListObject(){
        return topList;
    }

    public Location getLocation(){
        return location.clone();
    }

    public void setLocation(Location location){
        this.location = location.clone();
        saveParkour(folderName + dataFileNameInsideFolder);
    }

    public String getName() {
        return name;
    }

    public Material addBackBlock(String materialName){
        Material material = getMaterial(materialName);

        if(material == Material.AIR)
            throw new IllegalStateException("AIR can't be a backblock!");
        else if(hasBackBlock(material))
            throw new IllegalStateException(material.name() + " is already a backblock!");

        addBackBlock(material); // file changing in command section
        return material;
    }

    public void addBackBlock(Material material) {
        backBlocks.add(material);
    }

    public Material removeBackBlock(String materialName){
        Material material = getMaterial(materialName);

        if(!hasBackBlock(material))
            throw new IllegalStateException(material.name() + " is not a backblock!");

        removeBackBlock(material); // file changing in command section
        return material;
    }

    public void removeBackBlock(Material material) {
        backBlocks.remove(material);
    }

    public boolean hasAnyBackBlock(List<Material> materialList) {
        for (Material material : materialList){
            if (backBlocks.contains(material)) return true;
        }
        return false;
    }
    public boolean hasBackBlock(Material material){
        return backBlocks.contains(material);
    }

    private static Material getMaterial(String materialName){
        Material material = Material.matchMaterial(materialName);

        if(material == null)
            throw new IllegalStateException("\"" + materialName + "\" is not a material!");

        return material;
    }

    private void readData(BufferedReader reader) throws IOException {
        double x, y, z;
        float yaw, pitch;
        String worldName;
        int numberOfBackBlocks;
        String[] backBlockNamesSet;
        String categoryString;

        x = Double.parseDouble(reader.readLine());
        y = Double.parseDouble(reader.readLine());
        z = Double.parseDouble(reader.readLine());
        yaw = Float.parseFloat(reader.readLine());
        pitch = Float.parseFloat(reader.readLine());
        worldName = reader.readLine();
        name = reader.readLine();
        categoryString = reader.readLine();
        identifier = Integer.parseInt(reader.readLine());
        description = reader.readLine();
        numberOfBackBlocks = Integer.parseInt(reader.readLine());

        backBlockNamesSet = new String[numberOfBackBlocks];
        for (int i = 0; i < numberOfBackBlocks; i++){
            backBlockNamesSet[i] = reader.readLine();
            backBlocks.add(getMaterial(backBlockNamesSet[i]));
        }

        World world = Bukkit.getWorld(worldName);

        String line = reader.readLine();
        if (line != null) {
            int checkpointsNumber = Integer.parseInt(line);
            for (int i = 0; i < checkpointsNumber; i++) {
                String currentLine = reader.readLine();
                List<String> convertedLine = Stream.of(currentLine.split(",", -1))
                        .collect(Collectors.toList());
                double xCP = Double.parseDouble(convertedLine.get(0));
                double yCP = Double.parseDouble(convertedLine.get(1));
                double zCP = Double.parseDouble(convertedLine.get(2));
                Location checkpointLocation = new Location(world, xCP, yCP, zCP);
                checkpoints.add(checkpointLocation);
            }
        }


        location = new Location(world, x, y, z, yaw, pitch);
        category = ParkourCategory.valueOf(categoryString);
    }

    private void writeData(BufferedWriter writer) throws IOException {
        writer.write(location.getX()+"\n");
        writer.write(location.getY()+"\n");
        writer.write(location.getZ()+"\n");
        writer.write(location.getYaw()+"\n");
        writer.write(location.getPitch()+"\n");
        writer.write(location.getWorld().getName()+"\n");
        writer.write(name+"\n");
        writer.write(category.toString()+"\n");
        writer.write(identifier+"\n");
        writer.write(description+"\n");
        writer.write(backBlocks.size()+"\n");
        for (Material backBlock : backBlocks){
            writer.write(backBlock.name()+"\n");
        }
        if (!checkpoints.isEmpty()) {
            writer.write(checkpoints.size()+"\n");
            for(Location location : checkpoints){
                writer.write(location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ()+"\n");
            }
        }
    }
    public void loadParkour(String directory) {
        //Bukkit.getLogger().info(directory);
        if (!new File(directory + dataFileNameInsideFolder).exists()){
            getLogger().info("Missing file: " + directory + dataFileNameInsideFolder);
            return;
        }
        try{
            FileReader fileReader = new FileReader(directory + dataFileNameInsideFolder);
            BufferedReader reader = new BufferedReader(fileReader);
            readData(reader);
            reader.close();
            fileReader.close();

            //getLogger().info("wczytano parkour:\n      pkName = " + name + "\n      location = " + location.toString()
            //        + "\n      backBlocks = " + backBlocks);

            File topListFile = new File(directory + topList.fileNameInsideFolder);
            if (topListFile.exists()) {
                //getLogger().info("Wczytywanie topek...");
                topList.loadTopListString(directory);
            }
        } catch(IOException a){
            System.out.println("Błąd wczytywania parkoura");
            System.out.println(Arrays.toString(a.getStackTrace()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    public void saveParkour(String directory) {
        if (new File(directory).delete()){
            getLogger().info("Nadpisywanie parkoura: " + directory);
            FileCreator.createFile(directory);
        }
        try{
            FileWriter fileWriter = new FileWriter(directory);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writeData(writer);
            writer.close();
            fileWriter.close();

            getLogger().info("zapisano parkour:\n      pkName = " + name + "\n      location = " + location.toString()
                    + "\n      backBlocks = " + backBlocks);
        } catch(IOException r){
            System.out.println("E KURDE COS JEST NIE TAK DXD");
            System.out.println(Arrays.toString(r.getStackTrace()));
        }
    }
    private int generateID(){
        ParkourSet parkourSet = ParkourPlugin.parkourSet;
        return parkourSet.getOptimalIdentifierToAdd(parkourSet.getAllMapsOfCategory(this.category));
    }

    public boolean didPlayerFinishParkour(Player player){
        List<TopLine> topList = getTopListObject().getTopList();
        for (TopLine topLine : topList){
            if (topLine.player.getUniqueId().equals(player.getUniqueId())) return true;
        }
        return false;
    }
    public List<Location> getCheckpoints(){
        return checkpoints;
    }
}
