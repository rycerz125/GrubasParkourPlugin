package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveCommandTest {
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
    public void doesItRemove(){
        player.performCommand("apk add abc");
        player.nextMessage();
        player.performCommand("apk remove abc");
        player.nextMessage();
        player.performCommand("pk abc");
        assertEquals("Parkour with name \"abc\" doesn't exist!", player.nextMessage());
    }

    @Test
    public void removeParkourMessage(){
        player.performCommand("apk add abc");
        player.nextMessage();
        player.performCommand("apk add cde");
        player.nextMessage();
        player.performCommand("apk remove abc");
        assertEquals("Parkour with name \"abc\" removed!", player.nextMessage());
        player.performCommand("apk remove cde");
        assertEquals("Parkour with name \"cde\" removed!", player.nextMessage());
    }

    @Test
    public void noParkourWithThatName(){
        player.performCommand("apk remove dfgsdfgdsf");
        assertEquals("Parkour with name \"dfgsdfgdsf\" doesn't exist!", player.nextMessage());
    }
}
