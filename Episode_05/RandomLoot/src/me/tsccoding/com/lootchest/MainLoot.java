package me.tsccoding.com.lootchest;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MainLoot extends JavaPlugin implements Listener {
	public void onEnable(){
		loadConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		setupLoot(player);
	}

	private void setupLoot(Player player) {
		List<String> configItems = this.getConfig().getStringList("Items");
		
		int index = new Random().nextInt(configItems.size());
		String items = configItems.get(index);
		
		ItemStack newItem = new ItemStack(Material.getMaterial(items.toUpperCase()));
		
		player.getInventory().addItem(newItem);
	}
}
