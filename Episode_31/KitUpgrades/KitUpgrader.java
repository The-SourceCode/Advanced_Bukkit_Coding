package me.tsccoding.pretutorial;

public class KitUpgrader {
    private int damagelevel;
    private int xp;

    public KitUpgrader(int damagelevel, int xp) {
        this.damagelevel = damagelevel;

        this.xp = xp;
    }

    public int getDamagelevel() {
        return damagelevel;
    }

    public void setDamagelevel(int damagelevel) {
        this.damagelevel = damagelevel;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
