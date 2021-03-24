package org.chatting.client.model;

import org.chatting.common.message.Message;

import java.util.ArrayList;
import java.util.Collection;

public class NetworkModel {
    private final Collection<Message> pendingMessages = new ArrayList<>();
    private boolean shouldQuit;

    public boolean shouldQuit() {
        return shouldQuit;
    }

    public void setShouldQuit(boolean shouldQuit) {
        this.shouldQuit = shouldQuit;
    }

    public void sendMessage(Message message) {
        synchronized (pendingMessages) {
            pendingMessages.add(message);
        }
    }

    public boolean hasPendingMessages() {
        synchronized (pendingMessages) {
            return pendingMessages.size() > 0;
        }
    }

    public Collection<Message> clearPendingMessages() {
        synchronized (pendingMessages) {
            final Collection<Message> copy = new ArrayList<>(pendingMessages);
            pendingMessages.clear();
            return copy;
        }
    }
}
