package me.tsccoding.tutorial;

public class ArenaMethods {
    private TutorialMain plugin = TutorialMain.plugin;

    public void onDisableSetupArenas() {
        for (ArenaManager arenas : plugin.arenaManagerHashMap.values()) {
            plugin.getConfig().set(arenas.getName() + ".lobbyspawn", arenas.getLobbySpawn());
        }
    }

}
