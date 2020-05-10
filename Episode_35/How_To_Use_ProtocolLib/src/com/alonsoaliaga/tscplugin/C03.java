package com.alonsoaliaga.tscplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.InvocationTargetException;

public class C03 implements Listener {
    private Main plugin;
    public C03(Main main){
        this.plugin = main;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = pm.createPacket(PacketType.Play.Server.ANIMATION);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0,player.getEntityId()).write(1,0);
        try {
            pm.sendServerPacket(player,packet);
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK)return;
        Player player = e.getPlayer();
        if(e.getClickedBlock().getType() == Material.WALL_SIGN){
            Location loc = e.getClickedBlock().getLocation();
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            PacketContainer packet = pm.createPacket(PacketType.Play.Server.UPDATE_SIGN);
            packet.getModifier().writeDefaults();
            packet.getBlockPositionModifier().write(0,new BlockPosition(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
            packet.getChatComponentArrays().write(0,new WrappedChatComponent[]{
                    WrappedChatComponent.fromText("§3§lTheSourceCode"),
                    WrappedChatComponent.fromText("§l"+player.getName()),
                    WrappedChatComponent.fromText("§8Suscribe!"),
                    WrappedChatComponent.fromText("§4Suscribe!")
            });
            try {
                pm.sendServerPacket(player,packet);
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }else if(e.getClickedBlock().getType() == Material.SIGN_POST){
            Location loc = e.getClickedBlock().getLocation();
            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
            PacketContainer packet = pm.createPacket(PacketType.Play.Server.EXPLOSION);
            packet.getModifier().writeDefaults();
            packet.getDoubles().write(0,loc.getX()).write(1,loc.getY()).write(2,loc.getZ());
            packet.getFloat().write(0,(float)3);
            player.getWorld().getPlayers().forEach(online->{
                try {
                    pm.sendServerPacket(online,packet);
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }
}