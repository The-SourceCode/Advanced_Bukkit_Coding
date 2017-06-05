package me.tsccoding.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class MainEnchants extends JavaPlugin implements Listener {

	public CustomEnchantment ench = new CustomEnchantment(101);

	public void onEnable() {
		LoadEnchantments();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(ench, this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + ench.getName() + " I");
		meta.setDisplayName(ChatColor.GOLD + "Explosive Axe");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(ench, 1);
		
		player.getInventory().addItem(item);
	}

	@SuppressWarnings("unchecked")
	public void onDisable() {
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

			if (byId.containsKey(ench.getId())) {
				byId.remove(ench.getId());
			}

			if (byName.containsKey(ench.getName())) {
				byName.remove(ench.getName());
			}
		} catch (Exception ignored) {
		}
	}

	private void LoadEnchantments() {
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Enchantment.registerEnchantment(ench);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
