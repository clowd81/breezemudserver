package com.github.breezemudserver;

import com.github.breezemudserver.manager.ClientManager;
import com.github.breezemudserver.manager.DatabaseManager;

/**
 * Singleton class that holds an instance of all Managers currently being used.
 * This is to provide a global capability to keep track of managers and allow
 * Managers to communicate to one another.
 */
public class GameTracker {
    private static GameTracker instance = null;
    private ClientManager clientManager;
    private DatabaseManager databaseManager;
    private GameSettings gameSettings;
    
    private GameTracker() {
    }
    
    public static synchronized GameTracker getInstance() {
        if (instance == null) {
            instance = new GameTracker();
        }
        return instance;
    }
    
    public ClientManager getClientManager() {
        return clientManager;
    }
    
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    public GameSettings getGameSettings() {
        return gameSettings;
    }
    
    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
    
}
