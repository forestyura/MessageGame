package com.yuriivlasiuk.messagegame.service;

import java.io.Serializable;

/**
 * Object that is sent between connections
 */
public class Message implements Serializable {
    private String currentMessage = "";
    private String previousMessage = "";
    private int counter;

    public Message() {
    }

    public Message(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public String getPreviousMessage() {
        return previousMessage;
    }

    public void setPreviousMessage(String previousMessage) {
        this.previousMessage = previousMessage;
    }

    public void setCurrentMessageToPrevious() {
        if (!currentIsEmpty()) {
            previousMessage = currentMessage;
        }
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        if (previousIsEmpty()) {
            return "Message: " + currentMessage;
        } else {
            return "\nPrevious message: " + previousMessage + "\n" +
                    "counter = " + counter + "\n" +
                    "message: " + currentMessage;
        }
    }

    public boolean currentIsEmpty() {
        return currentMessage.trim().length() == 0;
    }

    public boolean previousIsEmpty() {
        return previousMessage.trim().length() == 0;
    }
}
