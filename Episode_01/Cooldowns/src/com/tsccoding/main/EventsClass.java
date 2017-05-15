package com.tsccoding.main;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;

public class EventsClass implements Listener {

	private MainCooldown plugin = MainCooldown.getPlugin(MainCooldown.class);

	@EventHandler
	public void onjoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		int cdtime = plugin.getConfig().getInt(uuid + ".Cooldown_Left");
		
		if (cdtime <= 0) {
			return;
		} else {
			plugin.cdtime.put(uuid, cdtime);
		}
	}

	@EventHandler
	public void onquit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		plugin.getConfig().set(uuid + ".Cooldown_Left", plugin.cdtime.get(uuid));
		plugin.saveConfig();
		plugin.cdtime.remove(uuid);
	}

	@EventHandler
	public void blockplace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		if (!plugin.cdtime.containsKey(uuid)) {
			if (block.getType().equals(Material.DIAMOND_BLOCK)) {
				plugin.cdtime.put(uuid, plugin.mastercd);
				player.sendMessage(ChatColor.GREEN + "You have been added to the cooldown!");
			}
		} else {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You still have " + ChatColor.YELLOW + plugin.cdtime.get(uuid)
					+ " seconds " + ChatColor.RED + "left till you can place Diamond Blocks!");
		}
	}
}
