package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.commands.apk.*;
import aybici.parkourplugin.commands.pklist.PrintAccessibleParkours;
import aybici.parkourplugin.commands.pklist.PrintAllParkours;
import aybici.parkourplugin.commands.pklist.PrintFinishedParkours;
import aybici.parkourplugin.commands.pklist.PrintUnfinishedParkours;
import aybici.parkourplugin.commands.times.PrintTimesCommand;
import aybici.parkourplugin.parkours.DisplayingTimesState;
import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;

public class CommandExecutorSetter {

    public static void setExecutors(ParkourPlugin plugin){

        plugin.getCommand("pkat").setExecutor(new PkatCommand());
        plugin.getCommand("pk").setExecutor(new PkCommand());
        plugin.getCommand("fails").setExecutor(new FailsCommand());
        plugin.getCommand("hide").setExecutor(new HideCommand());
        plugin.getCommand("ping").setExecutor(new PingCommand());
        plugin.getCommand("lobby").setExecutor(new LobbyCommand());
        plugin.getCommand("pkquit").setExecutor(new PkQuitCommand());
        plugin.getCommand("showlasttry").setExecutor(new ShowLastTryCommand());
        plugin.getCommand("playdemo").setExecutor(new PlayDemoCommand());

        SubcommandExecutor apkExecutor = new SubcommandExecutor("apk");
        SubcommandExecutor timesExecutor = new SubcommandExecutor("times");
        SubcommandExecutor pklistExecutor = new SubcommandExecutor("pklist");
        String categoryList = "easy/medium/hard/kz/community/dropper/special/event";

        pklistExecutor.addCommandExecutor(new Subcommand(
                "unfin",
                "[category: "+categoryList+"/all] [page]",
                "sends to player list of unfinished parkours from category",
                new PrintUnfinishedParkours()
        ));

        pklistExecutor.addCommandExecutor(new Subcommand(
                "fin",
                "[category: "+categoryList+"/all] [page]",
                "sends to player list of finished parkours from category",
                new PrintFinishedParkours()
        ));

        pklistExecutor.addCommandExecutor(new Subcommand(
                "all",
                "[category: "+categoryList+"/all] [page]",
                "sends to player list of all parkours from category",
                new PrintAllParkours()
        ));

        pklistExecutor.addCommandExecutor(new Subcommand(
                "acc",
                "[category: "+categoryList+"/all] [page]",
                "sends to player list of accessible parkours from category",
                new PrintAccessibleParkours()
        ));

        timesExecutor.addCommandExecutor(new Subcommand(
                "my",
                "[sort type: date/time/players] [page]",
                "sends to player all his times of parkour",
                new PrintTimesCommand(DisplayingTimesState.ONE_PLAYER_ALL_TIMES)
        ));

        timesExecutor.addCommandExecutor(new Subcommand(
                "top",
                "[sort type: date/time/players] [page]",
                "sends to player best times of all players",
                new PrintTimesCommand(DisplayingTimesState.ALL_PLAYERS_BEST_TIMES)
        ));

        timesExecutor.addCommandExecutor(new Subcommand(
                "all",
                "[sort type: date/time/players] [page]",
                "sends to player all times of parkour",
                new PrintTimesCommand(DisplayingTimesState.ALL_PLAYERS_ALL_TIMES)
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "addcp",
                "",
                "adds next checkpoint in player location",
                new AddCheckpointCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand(
                "removecp",
                "<number>",
                "removes checkpoint with given number",
                new RemoveCheckpointCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand(
                "rename",
                "<newName>",
                "sets parkour name",
                new RenameCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "globaldeltop",
                "<player/all>",
                "deletes ALL times in ALL parkours",
                new GlobalDeleteTopsCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "setid",
                "<number>",
                "sets ID",
                new SetIdentifier()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "setcat",
                "<"+categoryList+">",
                "sets category",
                new SetCategoryCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "deltop",
                "<player/all> [best]",
                "deletes times of the parkour",
                new DeleteTopsCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "add",
                "<name>",
                "adds new parkour",
                new AddCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "remove",
                "<name>",
                "removes parkour",
                new RemoveCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand("addbb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "adds new backblocks",
                new AddBackBlockCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand("removebb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "removes backblocks",
                new RemoveBackBlockCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand("setspawn"
                , ""
                , "sets new spawn point",
                new SetSpawnCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand("play"
                , ""
                , "teleports player to joined parkour",
                new PlayCommand()
        ));

        plugin.getCommand("apk").setExecutor(apkExecutor);
        plugin.getCommand("times").setExecutor(timesExecutor);
        plugin.getCommand("pklist").setExecutor(pklistExecutor);

    }
}
