package com.github.aybici;

public class HelpCommand {
    public static Subcommand newHelpCommand(SubcommandExecutor executor) {
        return new Subcommand("help",
                "",
                "shows all commands in plugin",
                (commandSender, command, s, strings) -> {
                    commandSender.sendMessage("Command list for " + executor.getCommandName() + ":");

                    for (Subcommand subcommand : executor.getSubcommands()) {
                        commandSender.sendMessage(subcommand.createHelpString());
                    }
                    return true;
                });
    }
}
