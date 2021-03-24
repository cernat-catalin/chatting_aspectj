package org.chatting.server.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<User> connectedUsers = new ArrayList<>();

    public void addUser(User user) {
        connectedUsers.add(user);
    }

    public List<User> getConnectedUsers() {
        return connectedUsers;
    }
}
