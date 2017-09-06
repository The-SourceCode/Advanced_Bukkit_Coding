package me.tsccoding.pretutorial;

public class PlayerLevelManager {

    private int level;
    private int xp;

    public PlayerLevelManager(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
