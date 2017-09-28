package me.tsccoding.pretutorial;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PreTutorial extends JavaPlugin implements Listener {
    private static PreTutorial instance;
    public HashMap<UUID, KitUpgrader> upgrades;
    public Commands commands;
    public Kits kits;

    @Override
    public void onEnable() {
        instance = this;
        commands = new Commands();
        kits = new Kits();
        upgrades = new HashMap<>();

        commands.onEnableMethods();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        upgrades.put(player.getUniqueId(), new KitUpgrader(0, 100));
    }

    @EventHandler
    public void damageEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            KitUpgrader kitUpgrader = upgrades.get(player.getUniqueId());

            event.setDamage(event.getDamage() + (kitUpgrader.getDamagelevel() * 2));
            player.sendMessage(kitUpgrader.getDamagelevel() * 2 + " Damage done.");
        }
    }

    @EventHandler
    public void invenClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            KitUpgrader kitUpgrader = upgrades.get(player.getUniqueId());

            if (event.getInventory().equals(kits.kitupgrade)) {
                event.setCancelled(true);
                if (clicked == null | !clicked.hasItemMeta()) return;

                if (clicked.getItemMeta().getDisplayName().equals(kits.damageupgrade.getItemMeta().getDisplayName())) {
                    int dmglevel = kitUpgrader.getDamagelevel();
                    int xp = kitUpgrader.getXp();

                    if (dmglevel == 0) {
                        kitUpgrader.setDamagelevel(1);
                        player.sendMessage(ChatColor.GOLD + "Your damage has been increased");
                        player.sendMessage(ChatColor.GREEN + "Costing: " + ChatColor.YELLOW + 20 + "XP");
                        kitUpgrader.setXp(kitUpgrader.getXp() - 20);
                        kits.createInventory(player);
                        return;
                    }

                    if (xp >= 20 * dmglevel) {
                        kitUpgrader.setDamagelevel(dmglevel + 1);
                        player.sendMessage(ChatColor.GOLD + "Your damage has been increased");
                        player.sendMessage(ChatColor.GREEN + "Costing: " + ChatColor.YELLOW + (20 * dmglevel) + "XP");
                        kitUpgrader.setXp(kitUpgrader.getXp() - (20 * dmglevel));
                        kits.createInventory(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have enough XP");
                        player.closeInventory();
                    }
                }
            }
        }
    }

    public static PreTutorial getInstance() {
        return instance;
    }


}