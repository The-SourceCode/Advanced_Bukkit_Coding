package com.alonsoaliaga.tscplugin;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public ConsoleCommandSender console;
    public ProtocolManager protocolManager;
    @Override
    public void onEnable() {
        console = Bukkit.getServer().getConsoleSender();
        console.sendMessage("[TSCPlugin] Plugin enabled!");
		new CO5(this);
    }
    @Override
    public void onDisable() {
        console.sendMessage("[TSCPlugin] Plugin disabled!");
    }
}