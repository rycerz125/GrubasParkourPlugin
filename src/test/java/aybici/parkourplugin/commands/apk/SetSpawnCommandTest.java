package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SetSpawnCommandTest {
    private PlayerMock player;

    @Before
    public void setUp()
    {
        ServerMock server = MockBukkit.mock();
        ParkourPlugin plugin = MockBukkit.load(ParkourPlugin.class);
        player = server.addPlayer();
        World world = player.getLocation().getWorld();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void playerIsNotOnAnyParkour(){
        player.performCommand("apk setspawn");
        assertEquals("You need to join parkour to use this command!", player.nextMessage());
    }

    @Test
    public void doesTheCommandWork(){
        player.performCommand("apk add sets1");
        player.nextMessage();
        player.assertTeleported(player.getLocation(), 1); // Clear last teleport
        Location newSpawnLocation = player.getLocation().clone().add(100,0,0);
        player.simulatePlayerMove(newSpawnLocation);
        player.performCommand("apk setspawn");
        assertEquals("Parkour spawn moved!", player.nextMessage());
        player.performCommand("pk sets1");
        player.assertTeleported(newSpawnLocation, 1);
    }
}
