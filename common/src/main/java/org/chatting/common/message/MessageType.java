package org.chatting.common.message;

import java.io.Serializable;

public enum MessageType implements Serializable {
    HANDSHAKE,
    SERVER_ANNOUNCEMENT,
    USER_CHAT, USER_QUIT
}
