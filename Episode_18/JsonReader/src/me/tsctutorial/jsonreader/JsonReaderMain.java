package me.tsctutorial.jsonreader;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReaderMain extends JavaPlugin {

    private File path = new File(this.getDataFolder() + "/custom_items");
    private final ArrayList<ItemStack> customItemsArray = new ArrayList<>();

    public void onEnable() {

        if (this.getServer().getOnlinePlayers().size() == 0) {
            return;
        }
        for (Player online : this.getServer().getOnlinePlayers()) {
            setupItems(online);
        }
    }

    private void setupItems(Player player) {
        for (File file : path.listFiles()) {
            try {
                JSONParser jsonParser = new JSONParser();
                Object parsed = jsonParser.parse(new FileReader(file.getPath()));
                JSONObject jsonObject = (JSONObject) parsed;
                String itemname = (String) jsonObject.get("itemname");

                JSONObject itemObject = (JSONObject) jsonObject.get("item");
                String itemType = (String) itemObject.get("id");

                ItemStack itemStack = new ItemStack(Material.valueOf(itemType.toUpperCase()));
                ItemMeta itemMeta = itemStack.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();

                JSONArray loreArray = (JSONArray) jsonObject.get("lore");

                for (Object lorelines : loreArray) {
                    JSONObject loredata = (JSONObject) lorelines;
                    String lore1 = (String) loredata.get("lore1");
                    String lore2 = (String) loredata.get("lore2");
                    String lore3 = (String) loredata.get("lore3");

                    lore.add(lore1);
                    lore.add(lore2);
                    lore.add(lore3);
                }

                itemMeta.setLore(lore);
                itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + itemname);
                itemStack.setItemMeta(itemMeta);

                player.getInventory().addItem(itemStack);


            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }


    }


}
