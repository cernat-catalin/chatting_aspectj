package org.chatting.server.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<User> connectedUserEntities = new ArrayList<>();

    public void addUser(User user) {
        connectedUserEntities.add(user);
    }

    public List<User> getConnectedUsers() {
        return connectedUserEntities;
    }
}
