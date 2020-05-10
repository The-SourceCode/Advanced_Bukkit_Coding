package com.alonsoaliaga.tscplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

public class C04 implements Listener {
    private Main plugin;
    public C04(Main main) {
        this.plugin = main;
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getBlock().getType()== Material.TNT){
            Location loc = e.getPlayer().getLocation();
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            PacketContainer packet = pm.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);
            packet.getModifier().writeDefaults();
            packet.getStrings().write(0,"mob.enderdragon.end");
            packet.getIntegers().write(0,loc.getBlockX()*8).write(1,loc.getBlockY()*8).write(2,loc.getBlockZ()*8).write(3,10);
            packet.getFloat().write(0, (float)1);
            try {
                pm.sendServerPacket(e.getPlayer(),packet);
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }else if(e.getBlock().getType()==Material.SPONGE){
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            double radius = 0.5;
            new BukkitRunnable() {
                double time = 0;
                @Override
                public void run() {
                    if(e.getPlayer().isSneaking() || !e.getPlayer().isOnline()){
                        cancel();
                        return;
                    }
                    Location loc = e.getPlayer().getLocation();
                    double x = radius*Math.sin(time);
                    double z = radius*Math.cos(time);
                    PacketContainer packet = pm.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
                    packet.getModifier().writeDefaults();
                    packet.getParticles().write(0, EnumWrappers.Particle.HEART);
                    packet.getFloat().write(0, (float)(loc.getX()+x)).write(1, (float)(loc.getY()+2)).write(2, (float)(loc.getZ()+z));
                    Bukkit.getOnlinePlayers().forEach(online->{
                        try {
                            pm.sendServerPacket(online,packet);
                        } catch (InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                    });
                    time+=0.1;
                }
            }.runTaskTimer(plugin,20,1);
        }else if(e.getBlock().getType()==Material.GLOWSTONE){
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(e.getPlayer().isSneaking()|| !e.getPlayer().isOnline()){
                        cancel();
                        return;
                    }
                    Color color = new Color(ThreadLocalRandom.current().nextInt(255),
                            ThreadLocalRandom.current().nextInt(255),
                            ThreadLocalRandom.current().nextInt(255));
                    Location loc = e.getPlayer().getLocation();
                    PacketContainer packet = pm.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
                    packet.getModifier().writeDefaults();
                    packet.getParticles().write(0, EnumWrappers.Particle.REDSTONE);
                    packet.getFloat().write(0, (float) loc.getX()).write(1, (float) loc.getY()).write(2, (float) loc.getZ())
                            .write(3, (float)(color.getRed()/255)).write(4, (float) color.getGreen()).write(5, (float) color.getBlue())
                            .write(6,1F);
                    try {
                        pm.sendServerPacket(e.getPlayer(),packet);
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                }
            }.runTaskTimer(plugin,20,1);
        }
    }
}
