package com.alonsoaliaga.tscplugin;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class C08 implements CommandExecutor {
    private Main plugin;
    public C08(Main instance) {
        plugin = instance;
        plugin.getCommand("adisguise").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cConsole cannot execute this command!");
            return true;
        }
        Player player = (Player) sender; // adisguise player/mob/block OBSIDIAN, CREEPER, "AlonsoAliaga"
        if(args.length<2){
            player.sendMessage("§cUsage: /adisguise [type] [name]");
            return true;
        }
        if(args[0].equalsIgnoreCase("player")){
            String name = args[1];
            DisguiseAPI.disguiseToAll(player,new PlayerDisguise(name).setViewSelfDisguise(false));
            player.sendMessage("§aYou disguised as '"+name+"'");
        }else if(args[0].equalsIgnoreCase("mob")){
            String entityname = args[1].toUpperCase();
            try{
                EntityType entityType = EntityType.valueOf(entityname);
                DisguiseAPI.disguiseToAll(player, new MobDisguise(DisguiseType.getType(entityType)).setViewSelfDisguise(false));
                player.sendMessage("§aYou disguised as '"+entityType.name()+"'");
            }catch (IllegalArgumentException e){
                player.sendMessage("&cThat's not a valid entity type!");
                return true;
            }
        }else if(args[0].equalsIgnoreCase("block")){
            String materialname = args[1].toUpperCase();
            try {
                Material material = Material.valueOf(materialname);
                DisguiseAPI.disguiseToAll(player, new MiscDisguise(DisguiseType.FALLING_BLOCK,material.getId()).setViewSelfDisguise(false));
                player.sendMessage("§aYou disguised as '"+material.name()+"'");
            }catch (IllegalArgumentException e){
                player.sendMessage("§cThat's not a valid material!");
                return true;
            }
        }else{
            player.sendMessage("§cUsage: /adisguise [player/mob/block] [name]");
        }
        return true;
    }
}
