package me.tsccoding.tutorial;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class TutorialMain extends JavaPlugin implements Listener {

    public WorldGuardPlugin worldGuardPlugin;

    public void onEnable() {
        worldGuardPlugin = getWorldGuard();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private ArrayList<Player> entered = new ArrayList<>();
    private ArrayList<Player> left = new ArrayList<>();

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (entered.contains(player) || left.contains(player)) {
            left.remove(player);
            entered.remove(player);
        }
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        enterRegion(player);
    }

    public void enterRegion(Player player) {
        LocalPlayer localPlayer = worldGuardPlugin.wrapPlayer(player);
        Vector playerVector = localPlayer.getPosition();
        RegionManager regionManager = worldGuardPlugin.getRegionManager(player.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);

        for (ProtectedRegion regions : applicableRegionSet) {
            if (regions.contains(playerVector)) {
                if (!entered.contains(player)) {
                    try {
                        left.remove(player);
                        entered.add(player);

                        String owners = regions.getOwners().toPlayersString();
                        owners = owners.replace("uuid:", "");

                        Player regionPlayer = this.getServer().getPlayer(UUID.fromString(owners));
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        player.sendMessage(ChatColor.GREEN + "Now Entering: ");
                        player.sendMessage(ChatColor.BLUE + regionPlayer.getName() + "'s property");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (!left.contains(player)) {
            if (applicableRegionSet.size() == 0) {
                entered.remove(player);
                left.add(player);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
}
