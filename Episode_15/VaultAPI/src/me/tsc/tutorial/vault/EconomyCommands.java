package me.tsc.tutorial.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommands implements CommandExecutor {

    private Economy economy = VaultMain.economy;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("eco")) {
                if (args[0].equalsIgnoreCase("deposit")) {
                    if (args.length == 3) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            int depositAmount = Integer.parseInt(args[2]);

                            economy.depositPlayer(target, depositAmount);
                            player.sendMessage(ChatColor.GRAY + "You have deposited §a$" + depositAmount + "§7 into §a" + target.getName() + "'s §7account");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("balance")) {
                    if (args.length == 2) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            int balance = (int) economy.getBalance(target);
                            player.sendMessage(ChatColor.GREEN + target.getName() + " §7has §a$" + balance + "§7 in their account");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
