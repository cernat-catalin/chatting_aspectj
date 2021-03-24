package org.chatting.common.message;

import java.io.Serializable;

public class UserChatMessage implements Message, Serializable {

    public String message;

    @Override
    public MessageType getMessageType() {
        return MessageType.USER_CHAT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
