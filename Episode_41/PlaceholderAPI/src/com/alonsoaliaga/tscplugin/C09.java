package com.alonsoaliaga.tscplugin;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class C09 implements Listener {
    private Main plugin;
    public C09(Main plugin){
        this.plugin = plugin;
        registerPlaceholders();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    private void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("tscplugin", new PlaceholderHook() {
            @Override
            public String onRequest(OfflinePlayer p, String params) {
                if(p!=null && p.isOnline()){
                    return onPlaceholderRequest(p.getPlayer(),params);
                }
                return null;
            }

            @Override
            public String onPlaceholderRequest(Player p, String params) {
                if(p==null){
                    return null;
                }
                // %tscplugin_PARAMS%
                if(params.equalsIgnoreCase("creator")){
                    return "AlonsoAliaga";
                }
                if(params.equalsIgnoreCase("isflying")){
                    return p.isFlying()?"Yes":"No";
                }
                if(params.equalsIgnoreCase("randomuser")){
                    List<String> users = Arrays.asList("AlonsoTwo","TheSourceCode","Peter","Revelation","The_SwaggerHD");
                    return users.get(new Random().nextInt(users.size()));
                }
                return null;
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String newmessage = PlaceholderAPI.setPlaceholders(p,"&6¡%vault_prefix% %player_name% &6has joined the server!");
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',newmessage));
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        ItemStack itemStack = p.getInventory().getItemInHand();
        if(itemStack!=null && itemStack.getType()== Material.DIAMOND_PICKAXE){
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§3§lTheSourceCode Pickaxe");
            List<String> lore = Arrays.asList("&7This pickaxe was forged by gods!"," &2&l&m---------------------&r ",
                    "&7Random user: &e%tscplugin_randomuser%","&7Is flying: &c%tscplugin_isflying%",
                    "&7Broken blocks: &6%statistic_mine_block%","&7Broken diamond ores: &b%statistic_mine_block_DIAMOND_ORE%");
            itemMeta.setLore(colorize(PlaceholderAPI.setPlaceholders(p,lore)));
            itemStack.setItemMeta(itemMeta);
        }
    }

    private List<String> colorize(List<String> list) {
        return list.stream().map(line->ChatColor.translateAlternateColorCodes('&',line)).collect(Collectors.toList());
    }

}
