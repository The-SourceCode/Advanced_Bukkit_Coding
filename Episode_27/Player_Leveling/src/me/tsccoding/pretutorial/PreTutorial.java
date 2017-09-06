package me.tsccoding.pretutorial;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class PreTutorial extends JavaPlugin implements Listener {

    private HashMap<UUID, PlayerLevelManager> levelManagerHashMap;

    @Override
    public void onEnable() {
        this.levelManagerHashMap = new HashMap<>();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        this.levelManagerHashMap.clear();
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");

        if (!player.hasPlayedBefore()) {
            player.sendMessage("§bWelcome, your level is §a0");

            this.levelManagerHashMap.put(player.getUniqueId(), new PlayerLevelManager(0, 0));
            this.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", 0);
            this.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
            this.saveConfig();

            this.setscore(player, 0, 0);
        } else {
            int level = this.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".level");
            int xp = this.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".xp");
            levelManagerHashMap.put(player.getUniqueId(), new PlayerLevelManager(level, xp));
            setscore(player, level, xp);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerLevelManager playerLevelManager = this.levelManagerHashMap.get(player.getUniqueId());

        if (this.levelManagerHashMap.containsKey(player.getUniqueId())) {
            this.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", playerLevelManager.getLevel());
            this.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", playerLevelManager.getXp());
            this.saveConfig();
            this.levelManagerHashMap.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void blockbreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerLevelManager playerLevelManager = this.levelManagerHashMap.get(player.getUniqueId());
        Block block = event.getBlock();

        if (block.getType() == Material.STONE) {
            playerLevelManager.setXp(playerLevelManager.getXp() + 100);
            player.sendMessage("§a+100 §bExperience");
            xpcheck(player, playerLevelManager);
            setscore(player, playerLevelManager.getLevel(), playerLevelManager.getXp());

        }
    }

    private void xpcheck(Player player, PlayerLevelManager playerLevelManager) {
        int xpneeded = this.getConfig().getInt("Levels.1.xp");
        int xp = playerLevelManager.getXp();

        if (xp  >=xpneeded ) {
            player.sendMessage("§6Leveled UP!");
            playerLevelManager.setLevel(1);
        }
    }

    private void setscore(Player player, int level, int xp) {
        Scoreboard scoreboard = this.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");

        objective.setDisplayName("§cPlayer Level");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score lvl = objective.getScore("Level: §a" + level);
        lvl.setScore(1);
        Score exp = objective.getScore("XP: §a" + xp);
        exp.setScore(0);

        player.setScoreboard(scoreboard);
    }


}