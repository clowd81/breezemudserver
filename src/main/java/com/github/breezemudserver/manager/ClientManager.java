package com.github.breezemudserver.manager;

import java.util.ArrayList;
import java.util.List;

import com.github.breezemudserver.server.ClientConnectionThread;
import com.github.breezemudserver.server.ClientMessage;

/**
 * This manager is responsible for starting, stopping, and keeping track of all
 * ClientConnectionThreads. It provides mechanisms to find
 * ClientConnectionThreads based upon certain criteria. It is responsible for
 * joining ClientConnectionThreads when they complete processing.
 */
public class ClientManager {
    // All accesses to clientList should be synchronized
    private List<ClientConnectionThread> clientList;
    
    public ClientManager() {
        clientList = new ArrayList<ClientConnectionThread>();
    }
    
    public synchronized void initNewClient(ClientConnectionThread clientThread) {
        clientList.add(clientThread);
        
        System.out.println("CMANAGER: Starting new client thread");
        
        clientThread.start();
    }
    
    public synchronized void sendMessageToAll(ClientMessage newMessage) {
        for (ClientConnectionThread clientConnection : clientList) {
            clientConnection.addNewMessage(newMessage);
        }
    }
    
    public synchronized void closeThread(ClientConnectionThread clientThread) {
        if (clientList.contains(clientThread)) {
            try {
                clientThread.join();
                clientList.remove(clientList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
