package org.chatting.server;

public class UserNotFoundException extends RuntimeException {

    private final String username;

    public UserNotFoundException(String username) {
        super(String.format("User not found: %s", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
