package org.chatting.server;

import org.chatting.common.message.HandshakeMessage;
import org.chatting.common.message.Message;
import org.chatting.common.message.ServerAnnouncementMessage;
import org.chatting.common.message.UserChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;

    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    private User user;
    private boolean shouldQuit = false;

    public UserThread(Socket socket, ChatServer server) throws IOException {
        this.socket = socket;
        this.server = server;

        this.reader = new ObjectInputStream(socket.getInputStream());
        this.writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            final User user = handleUserJoin();
            server.addUserName(user.getUsername());
            this.user = user;
            final ServerAnnouncementMessage serverAnnouncementMessage = new ServerAnnouncementMessage();
            serverAnnouncementMessage.setAnnouncement("New user connected:" + user.getUsername());
            server.broadcast(serverAnnouncementMessage, this);

            do {
                final Object obj = reader.readObject();
                processMessage(obj);
            } while (!shouldQuit);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error in org.chatting.server.UserThread. Will remove user. Error was: " + ex.getMessage());
        } finally {
            handleUserRemove();
        }
    }

    private void handleUserRemove() {
        server.removeUser(user.getUsername(), this);
    }

    private User handleUserJoin() {
        try {
            final Object obj = reader.readObject();
            if (obj instanceof HandshakeMessage) {
                final HandshakeMessage handshakeMessage = (HandshakeMessage) obj;
                final User user = new User(1, "catalin", "pass");
//                user.setUsername(handshakeMessage.getName());
                return user;
            } else {
                throw new RuntimeException("Expected a Handshake Message");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error parsing message from user. ", e);
        }
    }

    private void processMessage(Object obj) throws IOException {
        if (!(obj instanceof Message)) {
            throw new RuntimeException("Wrong message type. Should inherit from Message. Object: " + obj);
        }

        final Message message = (Message) obj;
        switch (message.getMessageType()) {
            case USER_CHAT:
                final UserChatMessage userChatMessage = (UserChatMessage) message;
                final ServerAnnouncementMessage serverAnnouncementMessage = new ServerAnnouncementMessage();
                serverAnnouncementMessage.setAnnouncement("[" + user.getUsername() + "]: " + userChatMessage.getMessage());
                System.out.printf("GOT user message: %s\n", userChatMessage.message);
                sendMessage(serverAnnouncementMessage);
                break;
            case USER_QUIT:
                shouldQuit = true;
                break;
            default:
                throw new RuntimeException("Unsupported message type in processing loop. Message Type: " + message.getMessageType());
        }

    }

    void sendMessage(Message message) throws IOException {
        writer.writeObject(message);
    }
}
