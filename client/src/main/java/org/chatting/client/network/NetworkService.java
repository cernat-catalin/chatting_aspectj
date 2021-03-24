package org.chatting.client.network;

import org.chatting.client.gui.EventQueue;
import org.chatting.client.model.NetworkModel;
import org.chatting.common.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkService {
    private final String hostname;
    private final int port;
    private final NetworkModel networkModel;
    private final EventQueue eventQueue;

    private Socket socket;
    private WriteThread writeThread;
    private ReadThread readThread;

    public NetworkService(String hostname, int port, NetworkModel networkModel, EventQueue eventQueue) {
        this.hostname = hostname;
        this.port = port;
        this.networkModel = networkModel;
        this.eventQueue = eventQueue;
    }

    public void start() {
        try {
            this.socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            this.writeThread = new WriteThread(socket, networkModel);
            this.readThread = new ReadThread(socket, networkModel, eventQueue);

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
            networkModel.setShouldQuit(true);
            writeThread.join();
            readThread.join();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        networkModel.sendMessage(message);
    }
}