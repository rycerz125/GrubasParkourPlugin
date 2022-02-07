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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AddCommandTest {
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
    public void addParkour(){
        Location parkour1 = player.getLocation().clone().add(300, 0, 400);
        Location parkour2 = player.getLocation().clone().add(600, 0, 200);

        player.simulatePlayerMove(parkour1);
        player.performCommand("apk add addpk1");
        assertEquals("Parkour with name \"addpk1\" added!", player.nextMessage());

        player.simulatePlayerMove(parkour2);
        player.performCommand("apk add addpk2");
        assertEquals("Parkour with name \"addpk2\" added!", player.nextMessage());

        player.performCommand("pk addpk1");
        player.assertTeleported(parkour1, 1);

        player.performCommand("pk addpk2");
        player.assertTeleported(parkour2, 1);
    }

    @Test
    public void nameAlreadyUsed(){
        player.performCommand("apk add abc");
        player.nextMessage();
        player.performCommand("apk add abc");
        assertEquals("Parkour with name \"abc\" already exists!", player.nextMessage());
    }

    @Test
    public void nameCantStartFromDigit(){
        player.performCommand("apk add 1abc");
        assertEquals("Parkour name can't start from a digit!", player.nextMessage());
    }

    @Test
    public void addParkourCommandAddsYouToParkour(){
        player.performCommand("apk add abc");
        player.nextMessage();
        player.performCommand("apk addbb air");
        assertNotEquals("You need to join parkour to use this command!", player.nextMessage());
    }
}
