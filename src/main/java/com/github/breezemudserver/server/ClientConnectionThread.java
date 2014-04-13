package com.github.breezemudserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.breezemudserver.manager.ClientManager;

/**
 * This thread handles all input and output connectivity with a client. It
 * relies upon the ClientManager to send messages to other client connections
 */
public class ClientConnectionThread extends Thread {
    
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private ClientStateEnum currentState;
    private ClientManager clientManager;
    private ConcurrentLinkedQueue<ClientMessage> messageQueue;
    private InputStream inputStream;
    
    public ClientConnectionThread(Socket socket, ClientManager clientManager) {
        this.socket = socket;
        this.clientManager = clientManager;
        messageQueue = new ConcurrentLinkedQueue<ClientMessage>();
    }
    
    public void addNewMessage(ClientMessage newMessage) {
        messageQueue.add(newMessage);
    }
    
    private void initSocket() {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            inputStream = socket.getInputStream();
            input = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void closeSocket() {
        try {
            socket.close();
            clientManager.closeThread(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String getInput() {
        String inVal = "";
        
        try {
            if (inputStream.available() > 2) {
                inVal = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inVal;
    }
    
    /**
     * Simple client processing loop. First obtain input from client and process
     * the command based upon the client state. Afterwards, check for outgoing
     * messages, process them, and send them to the client.
     */
    @Override
    public void run() {
        initSocket();
        
        currentState = ClientStateEnum.CLIENT_INIT_CONNECTION;
        
        System.out.println("CLIENT" + this.getId() + ": Starting new client thread");
        String input = "";
        input = getInput();
        
        input = "";
        
        while (!socket.isClosed()) {
            input = getInput();
            
            if (currentState == ClientStateEnum.CLIENT_INIT_CONNECTION) {
                
            }
            
            if ((input.length() > 0) && input.toLowerCase().contains("say")) {
                System.out.println("CLIENT" + this.getId() + ": SAY message received");
                ClientMessage newMessage = new ClientMessage(ClientMessageTypeEnum.CLIENT_MSG_SAY,
                        input.substring("say".length(), input.length()));
                clientManager.sendMessageToAll(newMessage);
            }
            
            if (messageQueue.size() > 0) {
                ClientMessage nextMessage = messageQueue.remove();
                
                switch (nextMessage.getMessageType()) {
                    case CLIENT_MSG_SAY:
                        output.print(nextMessage.getMessageContents());
                        output.flush();
                        break;
                    
                    default:
                        break;
                }
            }
        }
        
        System.out.println("CLIENT" + this.getId() + ": Client Disconnected");
        closeSocket();
    }
    
}
