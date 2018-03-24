package com.example.jennadeng.contextfirst;

public class ChatMessage {
    public boolean left;
    public String message;
    public String sentTime;
    public String name;
    public String role;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
        /*this.sentTime = message.getTime();
        this.role = message.getRole();
        this.name = message.getName();*/
    }
}