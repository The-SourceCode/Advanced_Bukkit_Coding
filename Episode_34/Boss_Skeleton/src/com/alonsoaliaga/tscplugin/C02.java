package com.alonsoaliaga.tscplugin;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class C02 implements Listener {
    private Main plugin;
    public C02(Main main){
        this.plugin = main;
    }
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(e.getBlock().getType()== Material.SPONGE){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            Skeleton skeleton = e.getBlock().getWorld().spawn(e.getBlock().getLocation().add(0.5,0,0.5),Skeleton.class);
            skeleton.setCustomName("§8§lSkeleton Boss");
            skeleton.setCustomNameVisible(true);
            skeleton.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            skeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            skeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            skeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            skeleton.setMetadata("SkeletonKing",new FixedMetadataValue(plugin,"skeletonking"));
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Skeleton && e.getDamager() instanceof Player){
            if(e.getEntity().hasMetadata("SkeletonKing")){
                int random = ThreadLocalRandom.current().nextInt(10);
                if(random<5){ //0 1 2 3 4
                    e.setCancelled(true);
                    Player player = (Player)e.getDamager();
                    player.playSound(player.getLocation(), Sound.valueOf("ANVIL_LAND"),10,10);
                    player.sendMessage("§c§lYour attack was blocked!");
                }
            }
        }
        if(e.getDamager() instanceof Skeleton && e.getEntity() instanceof Player){
            if(e.getDamager().hasMetadata("SkeletonKing")){
                int random = ThreadLocalRandom.current().nextInt(10);
                if(random<5){
                    e.setCancelled(true);
                    Player player = (Player)e.getEntity();
                    player.setVelocity(new Vector(0,2,0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,2));
                    player.sendMessage("§4§lYou were punched into the air!");
                }
            }
        }
    }
}