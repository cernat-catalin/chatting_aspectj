package org.chatting.client.network;

import org.chatting.client.model.NetworkModel;
import org.chatting.common.message.Message;
import org.chatting.common.message.UserDisconnectedMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

public class WriteThread extends Thread {
    private final NetworkModel networkModel;
    private final ObjectOutputStream writer;

    public WriteThread(Socket socket, NetworkModel networkModel) throws IOException {
        this.networkModel = networkModel;
        writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            while (!networkModel.shouldQuit()) {
                if (networkModel.hasPendingMessages()) {
                    final Collection<Message> messages = networkModel.clearPendingMessages();
                    for (Message message : messages) {
                        writer.writeObject(message);
                    }
                } else {
                    Thread.sleep(100);
                }
            }
            handleDisconnect();
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

    private void handleDisconnect() throws IOException {
        final UserDisconnectedMessage userDisconnectedMessage = new UserDisconnectedMessage();
        writer.writeObject(userDisconnectedMessage);
    }
}
