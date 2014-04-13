package com.github.breezemudserver;

public class GameSettings {
    private String motd;
    private String databaseType;
    private String databaseLocation;
    private int maxClients;
    
    public GameSettings() {
        
    }
    
    public String getMotd() {
        return motd;
    }
    
    public void setMotd(String motd) {
        this.motd = motd;
    }
    
    public String getDatabaseType() {
        return databaseType;
    }
    
    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
    
    public String getDatabaseLocation() {
        return databaseLocation;
    }
    
    public void setDatabaseLocation(String databaseLocation) {
        this.databaseLocation = databaseLocation;
    }
    
    public int getMaxClients() {
        return maxClients;
    }
    
    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }
    
}
