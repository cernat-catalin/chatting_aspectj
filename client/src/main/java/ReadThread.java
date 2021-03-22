import org.chatting.common.message.Message;
import org.chatting.common.message.ServerAnnouncementMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread extends Thread {
    private final ClientState clientState;
    private ObjectInputStream reader;

    public ReadThread(Socket socket, ClientState clientState) {
        this.clientState = clientState;

        try {
            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (!clientState.shouldQuit()) {
                final int available = reader.available();
                if (available > 0) {
                    final Object obj = reader.readObject();
                    processMessage(obj);
                } else {
                    Thread.sleep(200);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException ex) {
//                clientState.setShouldQuit(true);
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