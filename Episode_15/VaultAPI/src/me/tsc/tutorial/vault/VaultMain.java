package me.tsc.tutorial.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultMain extends JavaPlugin {

    public static Economy economy;

    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.shutdown();
        }

        this.getCommand("eco").setExecutor(new EconomyCommands());
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
