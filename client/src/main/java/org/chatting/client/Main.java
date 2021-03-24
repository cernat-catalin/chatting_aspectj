package org.chatting.client;

import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
//        org.chatting.client.model.ClientState clientState = new org.chatting.client.model.ClientState();
//        final String hostname = "localhost";
//        final int port = 8000;
//
//        ChatClient client = new ChatClient(hostname, port, clientState);
//        client.start();
        Application.launch(App.class, args);
    }
}
