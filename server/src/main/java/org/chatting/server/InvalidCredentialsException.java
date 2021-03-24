package org.chatting.server;

public class InvalidCredentialsException extends RuntimeException {

    private final String username;

    public InvalidCredentialsException(String username) {
        super(String.format("Invalid credentials for user: %s", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
