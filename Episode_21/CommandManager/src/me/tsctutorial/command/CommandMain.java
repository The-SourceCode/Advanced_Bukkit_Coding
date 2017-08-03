package me.tsctutorial.command;

import me.tsctutorial.command.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMain extends JavaPlugin {
    private static CommandMain instance;
    public CommandManager commandManager;

    public void onEnable() {
        setInstance(this);
        commandManager = new CommandManager();

        commandManager.setup();
    }

    public static CommandMain getInstance() {
        return instance;
    }

    private static void setInstance(CommandMain instance) {
        CommandMain.instance = instance;
    }
}
