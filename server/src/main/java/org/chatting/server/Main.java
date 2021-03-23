package org.chatting.server;

import org.chatting.server.database.DatabaseService;

public class Main {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Syntax: java org.chatting.server.ChatServer <port-number>");
//            System.exit(0);
//        }
//
//        int port = Integer.parseInt(args[0]);

        final HelloServer helloServer = new HelloServer();
        helloServer.sayHello();

        final DatabaseService databaseService = new DatabaseService();
        final User user = databaseService.getUser();
        System.out.printf("User: %d %s %s\n", user.getId(), user.getUsername(), user.getPassword());

        final int port = 8000;
        final ChatServer chatServer = new ChatServer(port);
        chatServer.execute();
    }
}
