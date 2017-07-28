package me.tsctutorial.fakeplayers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FPCommand implements CommandExecutor {

    private FakePlayersMain plugin = FakePlayersMain.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("fp")) {

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String npcName = args[1];
                        plugin.npcManager.createNPC(player, npcName);
                    }
                }
            }
        }
        return true;
    }
}
