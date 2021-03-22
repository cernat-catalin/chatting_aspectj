import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private final String hostname;
    private final int port;
    private final ClientState clientState;

    private Socket socket;
    private WriteThread writeThread;
    private ReadThread readThread;

    public ChatClient(String hostname, int port, ClientState clientState) {
        this.hostname = hostname;
        this.port = port;
        this.clientState = clientState;
    }

    public void start() {
        try {
            this.socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

//            new WriteThread(socket, clientState).start();
//            new ReadThread(socket, clientState).start();

            this.writeThread = new WriteThread(socket, clientState);
            this.readThread = new ReadThread(socket, clientState);

            writeThread.start();
            readThread.start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public void stop() {
        try {
            clientState.setShouldQuit(true);
            writeThread.join();
            readThread.join();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}