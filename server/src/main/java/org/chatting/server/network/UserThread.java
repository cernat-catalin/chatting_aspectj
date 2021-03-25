package org.chatting.server.network;

import org.chatting.common.message.*;
import org.chatting.server.database.DatabaseService;
import org.chatting.server.entity.UserStatisticsEntity;
import org.chatting.server.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class UserThread extends Thread {
    private final NetworkService server;
    private final DatabaseService databaseService;

    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;
    private boolean shouldQuit = false;

    private User user;

    public UserThread(Socket socket, NetworkService server, DatabaseService databaseService) throws IOException {
        this.server = server;
        this.databaseService = databaseService;
        this.reader = new ObjectInputStream(socket.getInputStream());
        this.writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            do {
                final Object obj = reader.readObject();
                if (!(obj instanceof Message)) {
                    throw new RuntimeException("Wrong message type. Should inherit from Message. Object: " + obj);
                }
                processMessage((Message) obj);
            } while (!shouldQuit);
        }
//        catch (UserNotFoundException | InvalidCredentialsException ex) {
//            System.out.printf("User credentials exception: s\n");
//        }
        catch (Exception ex) {
            System.out.println("Error in org.chatting.server.network.UserThread. Will remove user. Error was: " + ex.getMessage());
        } finally {
            handleUserRemove();
        }
    }

    private void handleUserRemove() {
        server.removeUser(this);
    }

    public void sendLoginResult(boolean result) throws IOException {
        System.out.println("Sencding login result message");
        final Message loginResultMessage = new LoginResultMessage(result);
        sendMessage(loginResultMessage);
    }

    private void sendSingUpResult(boolean result) throws IOException {
        System.out.println("Sending signup result message");
        final Message signupResultMessage = new SignupResultMessage(result);
        sendMessage(signupResultMessage);
    }

    private void processMessage(Message message) throws IOException {
        switch (message.getMessageType()) {
            case LOGIN:
                handleUserLogin((LoginMessage) message);
                break;
            case SIGN_UP:
                final boolean createdUser = handleUserSignup((SignupMessage) message);
                sendSingUpResult(createdUser);
                break;
            case USER_SEND_MESSAGE:
                final UserSendMessage userSendMessage = (UserSendMessage) message;
                final ChatMessage chatMessage = new ChatMessage(ChatMessage.AuthorType.USER,
                        user.getUsername(), userSendMessage.getMessage());
                server.broadcast(chatMessage);

                databaseService.incrementUserMessages(user.getUsername());
                sendUserStatistics();
                break;
            case USER_DISCONNECT:
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

    private void handleUserLogin(LoginMessage loginMessage) throws IOException {
        this.user = constructUser(loginMessage);
        sendLoginResult(true);

        databaseService.incrementUserLogins(user.getUsername());
        sendUserStatistics();
        server.sendConnectedUsersList();
        final String announcement = String.format("%s has joined the chat!", user.getUsername());
        final ChatMessage chatMessage = new ChatMessage(ChatMessage.AuthorType.SERVER, "Server", announcement);
        server.broadcast(chatMessage);
    }

    private User constructUser(LoginMessage loginMessage) {
        System.out.printf("Received login message from user: %s", loginMessage.getUsername());
        final String username = loginMessage.getUsername();
        final String password = loginMessage.getPassword();
        return new User(username, password);
    }

    private boolean handleUserSignup(SignupMessage signupMessage) {
        System.out.printf("Handling user signup\n");
        final String username = signupMessage.getUsername();
        final String password = signupMessage.getPassword();
        try {
            databaseService.addUser(username, password);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void sendUserStatistics() throws IOException {
        final Optional<UserStatisticsEntity> userStatisticsOpt = databaseService.getUserStatistics(user.getUsername());
        if (userStatisticsOpt.isPresent()) {
            final UserStatisticsEntity userStatisticsEntity = userStatisticsOpt.get();
            final UserStatisticsMessage userStatisticsMessage = new UserStatisticsMessage(
                    userStatisticsEntity.getNumberOfLogins(),
                    userStatisticsEntity.getNumberOfMessages());
            sendMessage(userStatisticsMessage);
        }
    }
}
