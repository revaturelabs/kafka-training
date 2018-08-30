package com.ex.kafkaspringdemo.domain;

public class ChatExchange {
    private String context;
    private String message;
    private String sender;

    public ChatExchange() {
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
