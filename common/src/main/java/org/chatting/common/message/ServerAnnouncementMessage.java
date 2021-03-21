package org.chatting.common.message;

import java.io.Serializable;

public class ServerAnnouncementMessage implements Message, Serializable {

    private String announcement;

    @Override
    public MessageType getMessageType() {
        return MessageType.SERVER_ANNOUNCEMENT;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
