package commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBackBlockCommandTest {
    private PlayerMock player;

    @Before
    public void setUp()
    {
        ServerMock server = MockBukkit.mock();
        ParkourPlugin plugin = MockBukkit.load(ParkourPlugin.class);
        player = server.addPlayer();
        player.setOp(true);
        World world = player.getLocation().getWorld();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void playerNotOnAnyParkour(){
        player.performCommand("apk addbb dirt");
        assertEquals("You need to join parkour to use this command!", player.nextMessage());
    }

    @Test
    public void playerSetsAirAsABackblock(){
        player.performCommand("apk add backblock1");
        player.nextMessage();
        player.performCommand("apk addbb air");
        assertEquals("AIR can't be a backblock!", player.nextMessage());
    }

    @Test
    public void playerSetsMaterialThatDoesntExist(){
        player.performCommand("apk add backblock2");
        player.nextMessage();
        player.performCommand("apk addbb alksfdjsdalkfjlasd");
        assertEquals("\"alksfdjsdalkfjlasd\" is not a material!", player.nextMessage());
    }

    @Test
    public void addingBackBlockProperly(){
        player.performCommand("apk add backblock3");
        player.nextMessage();
        player.performCommand("apk addbb dirt");
        assertEquals("Added backblock DIRT", player.nextMessage());
    }

    @Test
    public void isAlreadyABackblock(){
        player.performCommand("apk add backblock4");
        player.nextMessage();
        player.performCommand("apk addbb dirt");
        player.nextMessage();
        player.performCommand("apk addbb dirt");
        assertEquals("DIRT is already a backblock!", player.nextMessage());
    }
}
