package com.github.breezemudserver;

import com.github.breezemudserver.manager.DatabaseManager;
import com.github.breezemudserver.server.ServerThread;
import com.github.breezemudserver.startup.ObjectFileReader;

/**
 * Startup and initialize the Breeze MUD server. Kicks off ServerThread when
 * initialization is complete.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Initializing Breeze Server...");
        System.out.println("Initializing Game Tracker...");
        GameTracker gameTracker = GameTracker.getInstance();
        
        System.out.println("Reading in config files...");
        ObjectFileReader fileReader = new ObjectFileReader();
        
        fileReader.parseConfigFiles("example");
        
        System.out.println("Initializing Database...");
        DatabaseManager databaseManager = new DatabaseManager();
        gameTracker.setDatabaseManager(databaseManager);
        
        try {
            databaseManager.initDatabase(gameTracker.getGameSettings());
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Cannot load JDBC Driver for database");
        }
        
        ServerThread serverThread = new ServerThread();
        
        serverThread.start();
    }
}
