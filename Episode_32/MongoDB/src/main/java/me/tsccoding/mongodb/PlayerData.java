package me.tsccoding.mongodb;

public class PlayerData {

    private String uuid;
    private int gold;
    private int magic;

    public PlayerData(String uuid, int gold, int magic) {
        this.uuid = uuid;
        this.gold = gold;
        this.magic = magic;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }
}
