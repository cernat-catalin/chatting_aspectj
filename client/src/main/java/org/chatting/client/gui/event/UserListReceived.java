package org.chatting.client.gui.event;

import java.util.List;

public class UserListReceived implements Event {

    private final List<String> connectedUsers;

    public UserListReceived(List<String> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override
    public EventType getEventType() {
        return EventType.USER_LIST_RECEIVED;
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}
