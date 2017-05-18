package me.tscoding.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class MysqlSetterGetter implements Listener {

	MysqlMain plugin = MysqlMain.getPlugin(MysqlMain.class);

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		createPlayer(player.getUniqueId(), player);
	}

	@EventHandler
	public void hit(PlayerInteractEvent event) {
		updateCoins(event.getPlayer().getUniqueId());
		getCoins(event.getPlayer().getUniqueId());
	}

	public boolean playerExists(UUID uuid) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
				return true;
			}
			plugin.getServer().broadcastMessage(ChatColor.RED + "Player NOT Found");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void createPlayer(final UUID uuid, Player player) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			results.next();
			System.out.print(1);
			if (playerExists(uuid) != true) {
				PreparedStatement insert = plugin.getConnection()
						.prepareStatement("INSERT INTO " + plugin.table + " (UUID,NAME,COINS) VALUES (?,?,?)");
				insert.setString(1, uuid.toString());
				insert.setString(2, player.getName());
				insert.setInt(3, 500);
				insert.executeUpdate();

				plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCoins(UUID uuid) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("UPDATE " + plugin.table + " SET COINS=? WHERE UUID=?");
			statement.setInt(1, 1000);
			statement.setString(2, uuid.toString());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void getCoins(UUID uuid) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			results.next();
			
			System.out.print(results.getInt("COINS"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
