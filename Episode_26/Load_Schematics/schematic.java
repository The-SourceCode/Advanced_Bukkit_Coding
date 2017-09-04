package me.tsccoding.pretutorial;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.DataException;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PreTutorial
  extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void interact(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK))
    {
      loadSchematic(player);
      player.sendMessage(ChatColor.GREEN + "Schematic has been loaded.");
    }
  }

  private void loadSchematic(Player player)
  {
    Location location = player.getLocation();
    WorldEditPlugin worldEditPlugin = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
    File schematic = new File(getDataFolder() + File.separator + "/schematics/house.schematic");
    EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), 10000);
    try
    {
      CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
      clipboard.rotate2D(90);
      clipboard.paste(session, new Vector(location.getX(), location.getY(), location.getZ()), false);
    }
    catch (MaxChangedBlocksException|DataException|IOException e)
    {
      e.printStackTrace();
    }
  }
}
