package org.chatting.client.model;

import java.util.ArrayList;
import java.util.Collection;

public class NetworkModel {
    private final Collection<String> toSendMessages = new ArrayList<>();
    private boolean shouldQuit;

    public boolean shouldQuit() {
        return shouldQuit;
    }

    public void setShouldQuit(boolean shouldQuit) {
        this.shouldQuit = shouldQuit;
    }

    public void addMessageToSend(String message) {
        synchronized (toSendMessages) {
            toSendMessages.add(message);
        }
    }

    public boolean hasMessagesToSend() {
        synchronized (toSendMessages) {
            return toSendMessages.size() > 0;
        }
    }

    public Collection<String> clearToSendMessages() {
        synchronized (toSendMessages) {
            final Collection<String> copy = new ArrayList<>(toSendMessages);
            toSendMessages.clear();
            return copy;
        }
    }
}
