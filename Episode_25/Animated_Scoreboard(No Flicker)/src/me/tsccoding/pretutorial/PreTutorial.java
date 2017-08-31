package me.tsccoding.pretutorial;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class PreTutorial extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void joinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager scoreboardManager = this.getServer().getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Test", "Dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team health = scoreboard.registerNewTeam("health");
        health.addEntry("Health: §b");
        health.setSuffix("");
        health.setPrefix("");
        objective.getScore("Health: §b").setScore(0);
        String title = "SOURCECADE";
        char[] split = title.toCharArray();

        new BukkitRunnable() {
            int counter = 0;
            String finaltitle = "";

            @Override
            public void run() {
                if (counter < split.length) {
                    String letter = String.valueOf(split[counter]);
                    counter += 1;
                    String space = "";
                    for (int i = 0; i < split.length - counter; i++) {
                        space += " ";
                    }

                    finaltitle +=   letter;
                    objective.setDisplayName(finaltitle);
                    health.setSuffix(counter + "");
                }else{
                    String space = "";
                    finaltitle = "";
                    for (int i = 0; i < split.length - counter; i++) {
                        space += " ";
                    }
                    objective.setDisplayName(space);
                    counter = 0;
                }
            }
        }.runTaskTimer(this, 0, 10);

        player.setScoreboard(scoreboard);
    }
}