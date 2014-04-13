package com.github.breezemudserver.server;

public class ClientMessage {
    private ClientMessageTypeEnum messageType;
    private String messageContents;
    
    public ClientMessage(ClientMessageTypeEnum messageType, String messageContents) {
        this.messageType = messageType;
        this.messageContents = messageContents;
    }
    
    public ClientMessageTypeEnum getMessageType() {
        return messageType;
    }
    
    public void setMessageType(ClientMessageTypeEnum messageType) {
        this.messageType = messageType;
    }
    
    public String getMessageContents() {
        return messageContents;
    }
    
    public void setMessageContents(String messageContents) {
        this.messageContents = messageContents;
    }
    
}
