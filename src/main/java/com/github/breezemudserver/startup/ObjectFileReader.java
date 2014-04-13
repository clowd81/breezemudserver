package com.github.breezemudserver.startup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.github.breezemudserver.GameSettings;
import com.github.breezemudserver.GameTracker;
import com.github.breezemudserver.object.Race;
import com.github.breezemudserver.object.Room;
import com.github.breezemudserver.util.BreezeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ObjectFileReader {
    
    private ArrayList<Race> raceDataList;
    private ArrayList<Room> roomDataList;
    
    public ObjectFileReader() {
        raceDataList = new ArrayList<Race>();
        roomDataList = new ArrayList<Room>();
    }
    
    public void parseConfigFiles(String baseDir) {
        File root = new File(baseDir);
        Iterator<File> fileIterator = FileUtils.iterateFiles(root, null, true);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        
        while (fileIterator.hasNext()) {
            File dataFile = fileIterator.next();
            String path = dataFile.getAbsolutePath();
            
            if (path.contains(".json")) {
                
                try {
                    String content = BreezeUtil.readFile(path,
                            StandardCharsets.UTF_8);
                    
                    JsonElement data = parser.parse(content);
                    
                    String type = data.getAsJsonObject().get("type")
                            .getAsString().toLowerCase();
                    
                    if (type.equals("race")) {
                        Race newRace = gson.fromJson(data, Race.class);
                        
                        if (newRace != null) {
                            raceDataList.add(newRace);
                        }
                    } else if (type.equals("room")) {
                        Room newRoom = gson.fromJson(data, Room.class);
                        
                        if (newRoom != null) {
                            roomDataList.add(newRoom);
                        }
                    } else if (type.equals("settings")) {
                        GameSettings settings = gson.fromJson(data,
                                GameSettings.class);
                        
                        if (settings != null) {
                            GameTracker tracker = GameTracker.getInstance();
                            tracker.setGameSettings(settings);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    System.out.println("Json Syntax error in " + path + ": "
                            + e.getCause().getMessage());
                } catch (IllegalStateException e) {
                    System.out.println("Json stat exception error: " + path);
                }
            }
            System.out.println("");
        }
        
        System.out.println(raceDataList.size());
        System.out.println(roomDataList.size());
    }
}
