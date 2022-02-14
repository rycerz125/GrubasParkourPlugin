package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PkatCommandTest {
    private ServerMock server;
    private World world;
    private PlayerMock player;



    @Before
    public void setUp() {
        server = MockBukkit.mock();
        ParkourPlugin plugin = MockBukkit.load(ParkourPlugin.class);
        player = server.addPlayer();
        world = player.getLocation().getWorld();
    }



    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void doesItWork(){
        PlayerMock player2 = server.addPlayer("Player");
        player2.teleport(new Location(world, 100, 100, 100));
        player2.performCommand("apk add abc");
        player2.performCommand("pk abc");

        player.performCommand("pkat Player");
        player.assertTeleported(player2.getLocation(), 1);
    }

    @Test
    public void playerDoesntExist(){
        player.performCommand("pkat Player");
        assertEquals("Player with name \"Player\" doesn't exist!", player.nextMessage());
    }

    @Test
    public void playerIsNotOnAnyParkour(){
        PlayerMock player2 = server.addPlayer("Player2");
        player.performCommand("pkat Player2");
        assertEquals("This player doesn't play any parkour!", player.nextMessage());
    }
}
