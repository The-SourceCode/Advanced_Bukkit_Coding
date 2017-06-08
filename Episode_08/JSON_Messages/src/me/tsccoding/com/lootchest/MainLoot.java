package me.tsccoding.com.lootchest;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;

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
	public void interact(PlayerInteractEvent event){
		Player player = event.getPlayer();
		IChatBaseComponent comp = ChatSerializer
				.a("{\"text\":\"Hello\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§6This is totally not here\"}}");
		PacketPlayOutChat chat = new PacketPlayOutChat(comp);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
	}

}
