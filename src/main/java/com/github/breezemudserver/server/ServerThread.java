package com.github.breezemudserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.github.breezemudserver.manager.ClientManager;

/**
 * This thread is responsible for listening for incoming connections and passing
 * them to the Client manager.
 */
public class ServerThread extends Thread {
    private static ServerSocket listener;
    private ClientManager clientManager;
    
    public ServerThread() {
        clientManager = new ClientManager();
    }
    
    private void initServer() throws IOException {
        listener = new ServerSocket(27393);
    }
    
    private void serverLoop() throws IOException {
        while (true) {
            Socket socket = listener.accept();
            
            System.out
                    .println("SERVER: New client connected, initializing client thread");
            
            ClientConnectionThread newClientThread = new ClientConnectionThread(
                    socket, clientManager);
            clientManager.initNewClient(newClientThread);
        }
    }
    
    private void closeServer() {
        try {
            if (listener != null) {
                listener.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            initServer();
            
            serverLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }
        
    }
}
