package org.chatting.server;

import org.chatting.server.network.NetworkService;

public class Main {

    public static void main(String[] args) {
        final int port = 8000;
        final NetworkService networkService = new NetworkService(port);
        networkService.start();
    }
}
