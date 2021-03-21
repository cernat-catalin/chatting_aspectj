package org.chatting.common.message;

import java.io.Serializable;

public class HandshakeMessage implements Message, Serializable {

    private String name;
    private String description;

    @Override
    public MessageType getMessageType() {
        return MessageType.HANDSHAKE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
