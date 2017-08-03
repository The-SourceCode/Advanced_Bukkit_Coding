package me.tsctutorial.command.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    /*
    /<command> <subcommand> args[0] args[1]
     */

    public SubCommand() {
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
}
