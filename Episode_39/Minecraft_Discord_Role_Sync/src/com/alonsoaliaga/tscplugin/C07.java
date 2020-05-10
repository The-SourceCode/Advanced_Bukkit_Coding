package com.alonsoaliaga.tscplugin;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C07 implements Listener {
    private Main plugin;
    private HashMap<String, Role> permissionRoleMap;
    public C07(Main main) {
        this.plugin = main;
        permissionRoleMap = new HashMap<>();
        Bukkit.getScheduler().runTaskLater(plugin, this::loadRoles,40L);
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }
    private void loadRoles() {
        plugin.getConfig().getConfigurationSection("Role-sync").getKeys(false).forEach(s -> {
            String rolename = plugin.getConfig().getString("Role-sync."+s+".Name");
            if(plugin.c06.guild.getRolesByName(rolename,false).size()>0){
                plugin.console.sendMessage("§aRole "+rolename+" has been loaded!");
                String permission = plugin.getConfig().getString("Role-sync."+s+".Permission");
                Role role = plugin.c06.guild.getRolesByName(rolename,false).get(0);
                permissionRoleMap.put(permission,role);
            }
        });
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(plugin.c06.verifiedmembers.contains(player.getUniqueId())){
            plugin.c06.verifiedmembers.remove(player.getUniqueId());
            String discordid = plugin.playerData.getString("Data."+player.getUniqueId().toString());
            Member member = plugin.c06.guild.getMemberById(discordid);
            if(member==null)return;
            List<Role> rolestoadd = new ArrayList<>();
            List<Role> rolestoremove = new ArrayList<>();
            List<Role> memberroles = member.getRoles();
            permissionRoleMap.forEach((permission,role)->{
                if(player.hasPermission(permission)){
                    if(!memberroles.contains(role))rolestoadd.add(role);
                }else{
                    if(memberroles.contains(role))rolestoremove.add(role);
                }
            });
            if(!rolestoadd.isEmpty()){
                plugin.c06.guild.getController().addRolesToMember(member,rolestoadd).queue();
            }
            if(!rolestoremove.isEmpty()){
                Bukkit.getScheduler().runTaskLater(plugin,()->plugin.c06.guild.getController().removeRolesFromMember(member,rolestoremove).queue(),20L);
            }
        }
    }
}
