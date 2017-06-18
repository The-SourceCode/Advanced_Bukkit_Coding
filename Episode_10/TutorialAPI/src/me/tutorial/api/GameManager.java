package me.tutorial.api;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class GameManager {

	public void gameStart(Player player, int i, Location loc){
	}
	
	public void gameStop(Player player, int i, Location loc){
	}
	
	public void gamePlayerLeave(Player player){
	}
	
	public void gamePlayerJoin(Player player){
		player.sendMessage(ChatColor.YELLOW + "Hey welcome to the server!");
	}
}