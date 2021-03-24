package org.chatting.server.model;

import java.util.Random;

public class User {

    private final String username;
    private final String displayName;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.displayName = generateDisplayName(username);
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    private String generateDisplayName(final String username) {
        return String.format("%s@%05d", username, new Random().nextInt(99999));
    }
}
