package me.tsccoding.pretutorial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Kits {
    private PreTutorial plugin = PreTutorial.getInstance();
    public Inventory kitupgrade;
    public ItemStack damageupgrade;

    public void createInventory(Player player) {

        kitupgrade = Bukkit.createInventory(null, 9, "Kit Upgrades");

        //DAMAGE ITEM
        damageupgrade = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = damageupgrade.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Damage Upgrade");

        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Each upgrade will increase");
        lore.add(ChatColor.GRAY + "your damage by 2 hit points");
        lore.add("");
        checkLevel(damageupgrade, player, lore);

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        damageupgrade.setItemMeta(meta);

        kitupgrade.setItem(4, damageupgrade);

        player.openInventory(kitupgrade);
    }

    private void checkLevel(ItemStack itemStack, Player player, ArrayList<String> lore) {
        KitUpgrader kitUpgrader = plugin.upgrades.get(player.getUniqueId());

        if (itemStack.equals(damageupgrade)) {
            lore.add(ChatColor.WHITE + "Current Level: " + ChatColor.GOLD + kitUpgrader.getDamagelevel());
        }
    }

}
