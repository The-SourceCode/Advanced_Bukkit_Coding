package me.tsctutorial.fakeplayers;

import org.bukkit.plugin.java.JavaPlugin;

public class FakePlayersMain extends JavaPlugin {
    private static FakePlayersMain instance;

    public NPCManager npcManager;

    public static FakePlayersMain getInstance() {
        return instance;
    }

    private void setInstance(FakePlayersMain instance) {
        this.instance = instance;
    }

    public void onEnable() {
        setInstance(this);
        this.getCommand("fp").setExecutor(new FPCommand());
        this.npcManager = new NPCManager();
    }


}
