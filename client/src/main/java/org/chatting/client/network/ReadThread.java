package org.chatting.client.network;

import org.chatting.client.gui.EventQueue;
import org.chatting.client.gui.event.ChatMessageReceivedEvent;
import org.chatting.client.gui.event.Event;
import org.chatting.client.gui.event.LoginResultEvent;
import org.chatting.client.gui.event.UserListReceivedEvent;
import org.chatting.client.model.NetworkModel;
import org.chatting.common.message.ChatMessage;
import org.chatting.common.message.LoginResultMessage;
import org.chatting.common.message.Message;
import org.chatting.common.message.UserListMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class ReadThread extends Thread {

    private final NetworkModel networkModel;
    private final EventQueue eventQueue;
    private final ObjectInputStream reader;

    public ReadThread(Socket socket, NetworkModel networkModel, EventQueue eventQueue) throws IOException {
        this.networkModel = networkModel;
        this.eventQueue = eventQueue;
        this.reader = new ObjectInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            while (!networkModel.shouldQuit()) {
                final Object obj = reader.readObject();
                processMessage(obj);
            }
        } catch (SocketException ignored) {
            if (!networkModel.shouldQuit()) {
                throw new RuntimeException("Socket exception before client close");
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.printf("ERROR while closing reader stream %s\n", e);
            }
        }
    }

    private void processMessage(Object obj) {
        if (!(obj instanceof Message)) {
            throw new RuntimeException("Wrong message type. Should inherit from Message. Object: " + obj);
        }

        final Message message = (Message) obj;
        switch (message.getMessageType()) {
            case CHAT_MESSAGE:
                final ChatMessage chatMessage = (ChatMessage) message;

                final Event chatMessageReceived = new ChatMessageReceivedEvent(
                        ChatMessageReceivedEvent.AuthorType.valueOf(chatMessage.getAuthorType().name()),
                        chatMessage.getAuthorName(),
                        chatMessage.getMessage()
                );
                eventQueue.pushEvent(chatMessageReceived);
                break;
            case LOGIN_RESULT:
                System.out.printf("Received login result message\n");
                final LoginResultMessage loginResultMessage = (LoginResultMessage) message;

                final Event loginResultEvent = new LoginResultEvent(loginResultMessage.isLoginAccepted());
                eventQueue.pushEvent(loginResultEvent);
                break;
            case USER_LIST:
                System.out.printf("User list received network");
                final UserListMessage userListMessage = (UserListMessage) message;
                final Event userListReceived = new UserListReceivedEvent(userListMessage.getConnectedUsers());
                eventQueue.pushEvent(userListReceived);
                break;
            default:
                throw new RuntimeException("Unsupported message type in processing loop. Message Type: " + message.getMessageType());
        }

    }
}