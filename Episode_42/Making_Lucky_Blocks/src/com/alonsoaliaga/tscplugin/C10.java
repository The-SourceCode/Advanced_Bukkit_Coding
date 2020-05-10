package com.alonsoaliaga.tscplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class C10 implements Listener {
    private Main plugin;
    public C10(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getBlock().getType() != Material.SPONGE)return;
        Location location = e.getBlock().getLocation().add(0.5,0,0.5);
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        int randomnumber = new Random().nextInt(3); //0 1 2
        switch (randomnumber){
            case 0:{
                Witch witch = location.getWorld().spawn(location, Witch.class);
                Bat bat = location.getWorld().spawn(location, Bat.class);
                location.getWorld().spawn(location, Bat.class);
                location.getWorld().spawn(location, Bat.class);
                location.getWorld().spawn(location, Bat.class);
                location.getWorld().spawn(location, Bat.class);
                bat.setPassenger(witch);
                break;
            }
            case 1:{
                location.getWorld().spawn(location, Wolf.class);
                location.getWorld().dropItemNaturally(location,new ItemStack(Material.BONE,7));
                e.getPlayer().playSound(location, Sound.valueOf("WOLF_GROWL"),5,1);
                break;
            }
            case 2:{
                location.getWorld().createExplosion(location,2,true);
                break;
            }
        }
    }
}