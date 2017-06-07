package me.tsccoding.com.lootchest;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;

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
		
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, 
				ChatSerializer.a("{\"text\":\"§aWelcome\"}"),100,20,20);
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
	}
}
