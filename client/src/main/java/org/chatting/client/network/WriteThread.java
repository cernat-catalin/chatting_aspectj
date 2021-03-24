package org.chatting.client.network;

import org.chatting.client.model.NetworkModel;
import org.chatting.common.message.HandshakeMessage;
import org.chatting.common.message.UserChatMessage;
import org.chatting.common.message.UserQuitMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteThread extends Thread {
    private final NetworkModel networkModel;
    private final ObjectOutputStream writer;

    public WriteThread(Socket socket, NetworkModel networkModel) throws IOException {
        this.networkModel = networkModel;
        writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        final String userName = "Catalin";

        try {
            final HandshakeMessage handshakeMessage = new HandshakeMessage();
            handshakeMessage.setName(userName);
            handshakeMessage.setDescription("Some description");
            writer.writeObject(handshakeMessage);

            while (!networkModel.shouldQuit()) {
                if (networkModel.hasMessagesToSend()) {
                    networkModel.clearToSendMessages().forEach(message -> {
                        final UserChatMessage userChatMessage = new UserChatMessage();
                        userChatMessage.setMessage(message);
                        try {
                            writer.writeObject(userChatMessage);
                        } catch (IOException e) {
                            System.out.printf("Error while sending message %s\n", e);
                        }
                    });
                } else {
                    Thread.sleep(100);
                }
            }

            quit();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.printf("ERROR while closing writer stream %s\n", e);
            }
        }
    }

    public void quit() throws IOException {
        final UserQuitMessage userQuitMessage = new UserQuitMessage();
        writer.writeObject(userQuitMessage);
    }
}
