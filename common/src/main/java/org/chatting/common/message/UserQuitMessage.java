package org.chatting.common.message;

import java.io.Serializable;

public class UserQuitMessage implements Message, Serializable {

    @Override
    public MessageType getMessageType() {
        return MessageType.USER_QUIT;
    }
}
