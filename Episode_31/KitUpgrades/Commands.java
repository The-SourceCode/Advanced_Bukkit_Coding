package me.tsccoding.pretutorial;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private PreTutorial plugin = PreTutorial.getInstance();

    public void onEnableMethods(){
        plugin.getCommand("upgrade").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("upgrade")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                plugin.kits.createInventory(player);
            }
        }
        return true;
    }
}
