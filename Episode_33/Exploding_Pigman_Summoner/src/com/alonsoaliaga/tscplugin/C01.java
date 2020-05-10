package com.alonsoaliaga.tscplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class C01 implements Listener {
    private Main plugin;
    public C01(Main main){
        this.plugin = main;
    }
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(e.getBlock().getType().equals(Material.SPONGE)){
            Pig pig = (Pig)e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5,0,0.5), EntityType.PIG);
            pig.setCustomName(ChatColor.translateAlternateColorCodes('&',"&f&lHit me!"));
            pig.setCustomNameVisible(true);
            pig.setMetadata("Angry",new FixedMetadataValue(plugin,"angry"));
        }
    }
    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Pig && e.getDamager() instanceof Player){
            if(e.getEntity().hasMetadata("Angry")){
                Location location = e.getEntity().getLocation();
                e.getEntity().remove();
                location.getWorld().strikeLightning(location);
                PigZombie pigZombie = (PigZombie)location.getWorld().spawnEntity(location, EntityType.PIG_ZOMBIE);
                pigZombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                pigZombie.getEquipment().setItemInHand(new ItemStack(Material.GOLD_SWORD));
                pigZombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                startMinionSpawn(pigZombie,(Player)e.getDamager());
            }
        }
        if(e.getDamager() instanceof PigZombie && e.getEntity() instanceof Player){
            if(e.getDamager().hasMetadata("ExplodingZombie")){
                e.getDamager().getWorld().createExplosion(e.getDamager().getLocation(),3,true);
            }
        }
    }

    private void startMinionSpawn(PigZombie pigZombie, Player damager) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(pigZombie.isDead()){
                    cancel();
                    return;
                }
                PigZombie babyZombie = (PigZombie)pigZombie.getLocation().getWorld().spawnEntity(pigZombie.getLocation(), EntityType.PIG_ZOMBIE);
                babyZombie.setAngry(true);
                babyZombie.setBaby(true);
                babyZombie.getEquipment().setHelmet(new ItemStack(Material.TNT));
                babyZombie.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
                babyZombie.setMetadata("ExplodingZombie",new FixedMetadataValue(plugin,"explodingzombie"));
                babyZombie.setTarget(damager);
            }
        }.runTaskTimer(plugin,100,100);
    }
}
