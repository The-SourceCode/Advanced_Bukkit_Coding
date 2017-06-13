package me.tsccoding.custommob;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_11_R1.World;

public class CustomMobMain extends JavaPlugin implements Listener {

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		CustomEntityRegistry.registerCustomEntity(54, "Zombie", CustomZombie.class);
	}

	@EventHandler
	public void hit(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			Location loc = player.getLocation();

			World nmsworld = ((CraftWorld) loc.getWorld()).getHandle();

			CustomZombie zombie = new CustomZombie(nmsworld);

			zombie.setPosition(loc.getX(), loc.getY(), loc.getZ());
		}
	}

}
