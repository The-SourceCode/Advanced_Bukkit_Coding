package me.tsccoding.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class CreateArenaCMD implements CommandExecutor {
    private TutorialMain plugin = TutorialMain.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("arena")) {
                if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("create")) {
                        try {
                            String name = args[1];
                            int id = Integer.parseInt(args[2]);
                            boolean activated = Boolean.parseBoolean(args[3]);
                            plugin.arenaManagerHashMap.put(name, new ArenaManager(name, id, false, activated, new ArrayList<UUID>(), player.getLocation()));
                            player.sendMessage(ChatColor.GREEN + "Arena created");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "Invalid argument length.");
                    player.sendMessage(ChatColor.RED + "Usage: /arena create <arenaname> <id> <activated(boolean)>");
                }
            }
        }

        return true;
    }
}
