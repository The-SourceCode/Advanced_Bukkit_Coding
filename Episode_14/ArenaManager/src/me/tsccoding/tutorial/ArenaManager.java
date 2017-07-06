package me.tsccoding.tutorial;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class ArenaManager {

    private String name;
    private int id;
    private boolean gameStarted;
    private boolean activated;
    private ArrayList<UUID> playerInGame;
    private Location lobbySpawn;

    public ArenaManager(String name, int id, boolean gameStarted, boolean activated, ArrayList<UUID> playerInGame, Location lobbySpawn) {
        this.name = name;
        this.id = id;
        this.gameStarted = gameStarted;
        this.activated = activated;
        this.playerInGame = playerInGame;
        this.lobbySpawn = lobbySpawn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ArrayList<UUID> getPlayerInGame() {
        return playerInGame;
    }

    public void setPlayerInGame(ArrayList<UUID> playerInGame) {
        this.playerInGame = playerInGame;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }
}
