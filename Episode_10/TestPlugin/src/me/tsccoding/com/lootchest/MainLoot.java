package me.tsccoding.com.lootchest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.tutorial.api.TutorialAPI;

public class MainLoot extends JavaPlugin implements Listener {
	
	private TutorialAPI api = (TutorialAPI) Bukkit.getServer().getPluginManager().getPlugin("TutorialAPI");
	
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
		api.gameManager.gamePlayerJoin(player);
	}
	

}
