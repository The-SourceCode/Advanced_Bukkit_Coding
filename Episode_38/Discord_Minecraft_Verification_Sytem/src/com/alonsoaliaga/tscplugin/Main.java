package com.alonsoaliaga.tscplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin implements Listener {
    public ConsoleCommandSender console;
    public FileConfiguration playerData;
    public File data;
    private C06 c06;
    @Override
    public void onEnable() {
        console = Bukkit.getServer().getConsoleSender();
        createConfig();
        console.sendMessage("[TSCPlugin] Plugin enabled!");
        c06 = new C06(this);
    }
    private void createConfig() { //Simple way to create/copy a yml file from jar file.
        data = new File(getDataFolder() + File.separator + "data.yml");
        if (!data.exists()) {
            console.sendMessage(org.bukkit.ChatColor.LIGHT_PURPLE + "[TSCPlugin] Creating file data.yml");
            this.saveResource("data.yml", false);
        }
        playerData = new YamlConfiguration();
        try {
            playerData.load(data);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() { //Disconnects the bot when the plugin reloads or server is turned off.
        c06.jda.shutdownNow();
        console.sendMessage("[TSCPlugin] Plugin disabled!");
    }
}