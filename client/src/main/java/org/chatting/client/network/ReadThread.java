package org.chatting.client.network;

import org.chatting.client.model.NetworkModel;
import org.chatting.common.message.Message;
import org.chatting.common.message.ServerAnnouncementMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread extends Thread {

    private static final int SLEEP_BETWEEN_READ_CHECKS = 200;

    private final NetworkModel networkModel;
    private final ObjectInputStream reader;

    public ReadThread(Socket socket, NetworkModel networkModel) throws IOException {
        this.networkModel = networkModel;
        this.reader = new ObjectInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            while (!networkModel.shouldQuit()) {
                final boolean readAvailable = reader.available() > 0;
                if (readAvailable) {
                    final Object obj = reader.readObject();
                    processMessage(obj);
                } else {
                    Thread.sleep(SLEEP_BETWEEN_READ_CHECKS);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException ex) {
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
            case SERVER_ANNOUNCEMENT:
                final ServerAnnouncementMessage serverAnnouncementMessage = (ServerAnnouncementMessage) message;
                System.out.println("\n" + serverAnnouncementMessage.getAnnouncement());
                break;
            default:
                throw new RuntimeException("Unsupported message type in processing loop. Message Type: " + message.getMessageType());
        }

    }
}