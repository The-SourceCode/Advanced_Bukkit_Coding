package me.tsctutorial.metrics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SourceTutorialMetrics extends JavaPlugin {
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
    }
}
