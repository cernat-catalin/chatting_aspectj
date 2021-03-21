import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        final String hostname = "localhost";
        final int port = 8000;
        final ChatClient chatClient = new ChatClient(hostname, port);
        chatClient.execute();
    };
}
