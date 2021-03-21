import org.chatting.common.message.HandshakeMessage;
import org.chatting.common.message.UserChatMessage;
import org.chatting.common.message.UserQuitMessage;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteThread extends Thread {
    private Socket socket;
    private ChatClient client;
    private ObjectOutputStream writer;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        System.out.printf("HERE");
        final Console console = System.console();

        final String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);

        try {
            final HandshakeMessage handshakeMessage = new HandshakeMessage();
            handshakeMessage.setName(userName);
            handshakeMessage.setDescription("Some description");
            writer.writeObject(handshakeMessage);

            String text;
            do {
                text = console.readLine("[" + userName + "]: ");
//                System.out.printf("Read text: %s\n", text);
                if (text.equals("bye")) {
                    final UserQuitMessage userQuitMessage = new UserQuitMessage();
                    writer.writeObject(userQuitMessage);
                } else {
                    final UserChatMessage userChatMessage = new UserChatMessage();
                    userChatMessage.setMessage(text);
                    writer.writeObject(userChatMessage);
                }

            } while (!text.equals("bye"));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
