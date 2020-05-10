package com.alonsoaliaga.tscplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public ConsoleCommandSender console;
    @Override
    public void onEnable() {
        console = Bukkit.getServer().getConsoleSender();
        console.sendMessage("[TSCPlugin] Plugin enabled!");
	    getServer().getPluginManager().registerEvents(new C01(this),this);
    }
    @Override
    public void onDisable() {
        console.sendMessage("[TSCPlugin] Plugin disabled!");
    }
}
