package org.chatting.server.network;

import org.chatting.common.message.Message;
import org.chatting.server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private final int port;
    private final Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                final UserThread newUser = new UserThread(socket, this, null);
                userThreads.add(newUser);
                newUser.start();
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void broadcast(Message message, UserThread excludeUser) throws IOException {
        for (UserThread aUser : userThreads) {
//            if (aUser != excludeUser) {
                aUser.sendMessage(message);
//            }
        }
    }

    void removeUser(UserThread userThread) {
        userThreads.remove(userThread);
        final User user = userThread.getUser();
        if (user != null) {
            System.out.println("The user " + userThread.getUser().getDisplayName() + " has left");
        } else {
            System.out.println("A user has left before login");
        }
    }
}