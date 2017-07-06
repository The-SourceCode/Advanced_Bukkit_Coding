package me.tsccoding.tutorial;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class TutorialMain extends JavaPlugin implements Listener {

    public static TutorialMain plugin;
    public HashMap<String, ArenaManager> arenaManagerHashMap = new HashMap<>();

    public void onEnable() {
        plugin = this;
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("arena").setExecutor(new CreateArenaCMD());
        this.getServer().getPluginManager().registerEvents(this, this);
    }


}