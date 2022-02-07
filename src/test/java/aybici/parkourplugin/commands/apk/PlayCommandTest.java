package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayCommandTest {
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
    public void playerNotOnParkour(){
        player.performCommand("apk play");
        assertEquals("You need to join parkour to use this command!", player.nextMessage());
    }

    @Test
    public void doesItTeleportToParkourSpawn() {
        player.performCommand("apk add play1");
        player.assertTeleported(player.getLocation(), 1);
        player.performCommand("apk play");
        player.assertTeleported(player.getLocation(), 1);
    }
    @Test
    public void doesItChangeGamemode(){
        player.setGameMode(GameMode.CREATIVE);
        player.performCommand("apk add play2");
        player.performCommand("apk play");
        assertEquals(GameMode.ADVENTURE, player.getGameMode());
    }
}
