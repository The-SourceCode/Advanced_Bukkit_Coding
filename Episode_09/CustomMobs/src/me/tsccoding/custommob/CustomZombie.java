package me.tsccoding.custommob;

import org.bukkit.entity.Zombie;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.EntityChicken;
import net.minecraft.server.v1_11_R1.EntityPig;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.World;

public class CustomZombie extends EntityZombie {

	@SuppressWarnings("deprecation")
	public CustomZombie(World world) {
		super(world);
		
		Zombie craftZombie = (Zombie) this.getBukkitEntity();
		
		this.setBaby(true);
		
		craftZombie.setMaxHealth(50);
		
		this.setHealth(50);
		this.setCustomName(ChatColor.RED + "Zombie :D");
		this.setCustomNameVisible(true);
		
		this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPig>(this, EntityPig.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityChicken>(this, EntityChicken.class, true));

		this.getWorld().addEntity(this);
	}

}
