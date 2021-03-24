package org.chatting.common.message;

import java.io.Serializable;

public enum MessageType implements Serializable {
    LOGIN, LOGIN_RESULT,
    SERVER_ANNOUNCEMENT,
    USER_CHAT, USER_QUIT
}
