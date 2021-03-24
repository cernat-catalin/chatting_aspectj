package org.chatting.client.gui.event;

public class LoginButtonClick implements Event {

    private final String username;
    private final String password;

    public LoginButtonClick(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public EventType getEventType() {
        return EventType.LOGIN_BUTTON_CLICK;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
