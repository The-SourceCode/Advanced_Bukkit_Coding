package com.alonsoaliaga.tscplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public ConsoleCommandSender console;
    public C08 c08;
    @Override
    public void onEnable() {
        console = Bukkit.getServer().getConsoleSender();
        createConfig();
        console.sendMessage("[TSCPlugin] Plugin enabled!");
        c08 = new C08(this);
    }
    private void createConfig() {
        saveDefaultConfig();
    }
    @Override
    public void onDisable() {
        console.sendMessage("[TSCPlugin] Plugin disabled!");
    }
}