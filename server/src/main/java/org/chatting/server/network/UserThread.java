package org.chatting.server.network;

import org.chatting.common.message.*;
import org.chatting.server.database.DatabaseService;
import org.chatting.server.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class UserThread extends Thread {
    private final ChatServer server;
    private final DatabaseService databaseService;

    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;
    private boolean shouldQuit = false;

    private User user;

    public UserThread(Socket socket, ChatServer server, DatabaseService databaseService) throws IOException {
        this.server = server;
        this.databaseService = databaseService;

        this.reader = new ObjectInputStream(socket.getInputStream());
        this.writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            final Optional<User> userOpt = handleUserLogin();
            if (userOpt.isEmpty()) {
                System.out.println("User disconnected before login!");
            } else {
                sendLoginResult();
                this.user = userOpt.get();
                final ServerAnnouncementMessage serverAnnouncementMessage = new ServerAnnouncementMessage();
                serverAnnouncementMessage.setAnnouncement("New user connected:" + this.user.getUsername());
                server.broadcast(serverAnnouncementMessage, this);
                do {
                    final Object obj = reader.readObject();
                    processMessage(obj);
                } while (!shouldQuit);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error in org.chatting.server.network.UserThread. Will remove user. Error was: " + ex.getMessage());
        } finally {
            handleUserRemove();
        }
    }

    private void handleUserRemove() {
        server.removeUser(this);
    }

    private Optional<User> handleUserLogin() throws IOException, ClassNotFoundException {
        final Object obj = reader.readObject();
        if (obj instanceof LoginMessage) {
            final LoginMessage loginMessage = (LoginMessage) obj;
            System.out.printf("Received login message from user: %s", loginMessage.getUsername());
            final String username = loginMessage.getUsername();
            final String password = loginMessage.getPassword();
            return Optional.of(new User(username, password));
        } else {
            return Optional.empty();
        }
    }

    private void sendLoginResult() throws IOException {
        System.out.println("Sencding login result message");
        final Message loginResultMessage = new LoginResultMessage(true);
        sendMessage(loginResultMessage);
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

    public User getUser() {
        return user;
    }
}
