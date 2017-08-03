package me.tsctutorial.command.commands;

import me.tsctutorial.command.CommandMain;
import org.bukkit.entity.Player;

public class InfoCommand extends SubCommand {
    private CommandMain plugin = CommandMain.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage("This info is cool!");
    }

    @Override
    public String name() {
        return plugin.commandManager.info;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
