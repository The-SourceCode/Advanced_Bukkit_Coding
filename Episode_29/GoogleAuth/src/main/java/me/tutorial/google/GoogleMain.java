package me.tutorial.google;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.rmi.activation.ActivationSystem;
import java.util.ArrayList;
import java.util.UUID;

public class GoogleMain extends JavaPlugin implements Listener {
    private ArrayList<UUID> authlocked;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        authlocked = new ArrayList<UUID>();

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!this.getConfig().contains("authcodes." + player.getUniqueId())) {
            GoogleAuthenticator gAuth = new GoogleAuthenticator();
            GoogleAuthenticatorKey key = gAuth.createCredentials();

            player.sendMessage("§7Your §bGoogle Auth Code §7is §a" + key.getKey());
            player.sendMessage("§7You must enter this code in the Google Authenticator App before leaving the server.");

            this.getConfig().set("authcodes." + player.getUniqueId(), key.getKey());
            this.saveConfig();
        } else {
            authlocked.add(player.getUniqueId());
            player.sendMessage("§cPlease open the Google Authenticator App and provide the six digit code.");
        }
    }

    private boolean playerInputCode(Player player, int code) {
        String secretkey = this.getConfig().getString("authcodes." + player.getUniqueId());

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean codeisvalid = gAuth.authorize(secretkey, code);


        if (codeisvalid) {
            authlocked.remove(player.getUniqueId());
            return codeisvalid;
        }

        return codeisvalid;
    }


    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (authlocked.contains(player.getUniqueId())) {
            try {
                Integer code = Integer.parseInt(message);
                if (playerInputCode(player, code)) {
                    authlocked.remove(player.getUniqueId());
                    player.sendMessage("§a*Access Granted* §bWelcome to the server!");
                } else {
                    player.sendMessage("§cIncorrect or expired code ** A code will only contain numbers **");

                }
            } catch (Exception e) {
                player.sendMessage("§cIncorrect or expired code ** A code will only contain numbers **");
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (authlocked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockbreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (authlocked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockplace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (authlocked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
