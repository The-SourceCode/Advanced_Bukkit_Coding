package me.tsctutorial.economy;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class EconomyMain extends JavaPlugin {

    public static EconomyMain getInstance;
    public EconomyImplementer economyImplementer;
    private VaultHook vaultHook;

    public final HashMap<UUID,Double> playerBank = new HashMap<>();

    public void onEnable() {
        runOnEnable();
    }

    private void instanceClasses() {
        getInstance = this;
        economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
    }

    public void runOnEnable(){
        instanceClasses();
        vaultHook.hook();
        this.getCommand("econo").setExecutor(new EconomyCommands());
    }

    public void onDisable() {
        vaultHook.unhook();
    }
}
