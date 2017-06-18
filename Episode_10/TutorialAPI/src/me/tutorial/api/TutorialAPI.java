package me.tutorial.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TutorialAPI extends JavaPlugin implements Listener{
	
	public GameManager gameManager;
	
	public void onEnable(){
		gameManager = new GameManager();
	}
	
}
