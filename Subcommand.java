package com.github.aybici;

import org.bukkit.command.CommandExecutor;

public class Subcommand {
    private final String name;
    private final CommandExecutor executor;
    private final String description;
    private final String argsString;
    private final int[] possibleArgsCount;
    private String parentCommandName;

    public Subcommand(String name, String argsString, String description, CommandExecutor executor){
        this.name = name;
        this.argsString = argsString;
        this.description = description;
        this.possibleArgsCount = ArgsCounter.countArgs(argsString);
        this.executor = executor;
    }

    public String createUsageString(){
        return parentCommandName + " " + name + (argsString.length() > 0 ? " " : " ") + argsString;
    }

    public String createHelpString(){
        return createUsageString() + " - " + description;
    }

    public boolean isGoodUsage(String[] usage){
        int argsCount = usage.length;
        for(int num : possibleArgsCount){
            if(argsCount == num)
                return true;
        }
        return false;
    }

    public void setParentCommandName(String parentCommandName){
        this.parentCommandName = parentCommandName;
    }

    public String getName(){
        return name;
    }

    public CommandExecutor getExecutor(){
        return executor;
    }
}
