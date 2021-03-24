package org.chatting.client.gui;

public interface Event {

    enum EventType {
        CHANGE_PANEL,
        SEND_BUTTON_CLICK
    }

    EventType getEventType();
}
