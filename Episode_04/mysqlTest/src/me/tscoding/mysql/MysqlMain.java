package me.tscoding.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MysqlMain extends JavaPlugin implements Listener {
	private Connection connection;
	public String host, database, username, password, table;
	public int port;

	public void onEnable() {
		loadConfig();
		mysqlSetup();
		
		this.getServer().getPluginManager().registerEvents(new MysqlSetterGetter(), this);
	}
	
	public void loadConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void mysqlSetup() {
		host = this.getConfig().getString("host");
		port = this.getConfig().getInt("port");
		database = this.getConfig().getString("database");
		username = this.getConfig().getString("username");
		password = this.getConfig().getString("password");
		table = this.getConfig().getString("table");

		try {

			synchronized (this) {
				if (getConnection() != null && !getConnection().isClosed()) {
					return;
				}

				Class.forName("com.mysql.jdbc.Driver");
				setConnection(
						DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
								this.username, this.password));

				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
