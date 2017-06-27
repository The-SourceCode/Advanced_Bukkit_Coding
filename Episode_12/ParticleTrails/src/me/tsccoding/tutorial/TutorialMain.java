package me.tsccoding.tutorial;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;

public class TutorialMain extends JavaPlugin implements Listener {

    public static TutorialMain plugin;

    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(this, this);

        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        float red = 255;
                        float green =0;
                        float blue = 0;
                        Location location = player.getLocation();

                        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, (float)255, 0, 10);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
                    }
                }.runTaskTimerAsynchronously(this, 0, 0);
            }
        }
    }


}