public class Main {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Syntax: java ChatServer <port-number>");
//            System.exit(0);
//        }
//
//        int port = Integer.parseInt(args[0]);

        final HelloServer helloServer = new HelloServer();
        helloServer.sayHello();

        final int port = 8000;
        final ChatServer chatServer = new ChatServer(port);
        chatServer.execute();
    }
}
