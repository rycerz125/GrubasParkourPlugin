package aybici.parkourplugin.blockabovereader;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpecialBlockFinder {

    private static final HashSet<Material> notFullHeightMaterials = new HashSet<>(Arrays.asList(
            Material.CHEST,
            Material.ENDER_CHEST,
            Material.CACTUS
    ));
    private static boolean isCarpet(Material material){
        return material.name().contains("CARPET");
    }
	private static boolean isFenceOrWall(Block block){
		return block.getType().name().contains("WALL") || block.getType().name().contains("FENCE");
	}
    private static boolean isBottomSlab(Block block){
        if(!block.getType().name().contains("SLAB")) return false;
        Slab slab = (Slab) block.getBlockData();
        return slab.getType() == Slab.Type.BOTTOM;
    }
    private static boolean isBlockNotFullHeight(Block block){
        return isCarpet(block.getType()) ||
                isBottomSlab(block) ||
                notFullHeightMaterials.contains(block.getType());
    }

    private static int[] getEdgeCollisionsTable(Location playerLocation){
        int[] collidesWithEdges = {0,0}; //X, Z
        if ((int)playerLocation.getX() != (int)(playerLocation.getX() + 0.3))
            collidesWithEdges[0] = 1;
        else if ((int)playerLocation.getX() != (int)(playerLocation.getX() - 0.3)){
            collidesWithEdges[0] = -1;
        }
        if ((int)playerLocation.getZ() != (int)(playerLocation.getZ() + 0.3))
            collidesWithEdges[1] = 1;
        else if ((int)playerLocation.getZ() != (int)(playerLocation.getZ() - 0.3)){
            collidesWithEdges[1] = -1;
        }

        return  collidesWithEdges;
    }
    private static void addSuspectedBlocksHorizontally(int[] edgeCollisionsTable, Location playerLocation, // constant Y
                                                       Set<Block> potentiallySpecialBlocks){ //modyfikacja przez referencję
        Block mainBlock = playerLocation.getBlock();
        potentiallySpecialBlocks.add(mainBlock); // dodajemy blok w punkcie, w którym jest gracz
        boolean isMainBlockPassable = mainBlock.isPassable();
        boolean isMainBlockNotFullHeight = isBlockNotFullHeight(mainBlock);
        if(edgeCollisionsTable[0] != 0 && (isMainBlockPassable || isMainBlockNotFullHeight)){
            potentiallySpecialBlocks.add(playerLocation.clone(). // tylko delta X
                    add(edgeCollisionsTable[0] , 0 , 0).
                    getBlock());

            if (edgeCollisionsTable[1] != 0){ // delta Z + X
                potentiallySpecialBlocks.add(playerLocation.clone().
                        add(edgeCollisionsTable[0] , 0, edgeCollisionsTable[1]).
                        getBlock());
            }
        }
        if (edgeCollisionsTable[1] != 0 && (isMainBlockPassable || isMainBlockNotFullHeight)){ // tylko delta Z
            potentiallySpecialBlocks.add(playerLocation.clone().
                    add(0 , 0, edgeCollisionsTable[1]).
                    getBlock());
        }
    }

    private static void findPotentiallySpecialBlocks(Location playerLocation,
                                                     Set<Block> potentiallySpecialBlocks){ // modyfikacja przez referencję
        int[] edgeCollisionsTable = getEdgeCollisionsTable(playerLocation);
        addSuspectedBlocksHorizontally(edgeCollisionsTable, playerLocation,potentiallySpecialBlocks);
        // teraz dla y -= 1
        Location downLocation = playerLocation.clone().add(0, -1, 0);
        addSuspectedBlocksHorizontally(edgeCollisionsTable, downLocation,potentiallySpecialBlocks);
    }
    private static boolean isBlockTypeSemiPassable(Block block){      // bloki polprzewodzące
        return block.getType() == Material.LAVA ||
                block.getType() == Material.WATER ||
                block.getType() == Material.COBWEB;
    }
    private static boolean isBlockTypeInteractive(Block block){
        return block.getType() == Material.LIME_WOOL ||
                block.getType() == Material.RED_WOOL;
    }
    private static boolean checkBlockTypeDependingCollision(Location playerLocation, Block block){
        if (isBlockTypeInteractive(block) || isFenceOrWall(block)){
            return true;
        }
        else if(isBlockTypeSemiPassable(block)){
            return ((int)block.getLocation().getY()) == ((int)playerLocation.getY());
        } else{
            return ((int)block.getLocation().getY()) == ((int)(playerLocation.getY() - 0.5)) ||
                    ((int)block.getLocation().getY()) == ((int)playerLocation.getY());
        }
    }
    public static Set<Material> getCollidingBlockMaterials(Location playerLocation){
        Set<Block> potentiallySpecialBlocks = new HashSet<>();
        findPotentiallySpecialBlocks(playerLocation,potentiallySpecialBlocks);
        Set<Material> suspectedMaterialList = new HashSet<>();
        for (Block block : potentiallySpecialBlocks){
            if (checkBlockTypeDependingCollision(playerLocation, block))
                suspectedMaterialList.add(block.getType());
        }

        return suspectedMaterialList;
    }

}
