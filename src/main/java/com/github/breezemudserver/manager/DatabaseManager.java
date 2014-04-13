package com.github.breezemudserver.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.breezemudserver.GameSettings;

public class DatabaseManager {
    
    Connection connection;
    
    public DatabaseManager() {
        connection = null;
    }
    
    public void initDatabase(GameSettings settings) throws ClassNotFoundException {
        String dbType = settings.getDatabaseType().toLowerCase();
        String dbLocation = settings.getDatabaseLocation().toLowerCase();
        
        if (dbType.equals("sqlite")) {
            Class.forName("org.sqlite.JDBC");
            
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);
                
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
