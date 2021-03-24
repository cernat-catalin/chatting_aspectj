package org.chatting.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.chatting.client.gui.EventQueue;
import org.chatting.client.gui.EventThread;
import org.chatting.client.gui.SceneManager;
import org.chatting.client.model.NetworkModel;
import org.chatting.client.network.NetworkService;

public class App extends Application {

    private final NetworkModel networkModel;
    private final NetworkService networkService;
    private final SceneManager sceneManager;
    private final EventThread eventThread;

    public App() {
        final String hostname = "localhost";
        final int port = 8000;
        this.networkModel = new NetworkModel();

        final EventQueue eventQueue = new EventQueue();
        this.networkService = new NetworkService(hostname, port, networkModel, eventQueue);

        this.sceneManager = new SceneManager(eventQueue);
        this.eventThread = new EventThread(eventQueue, sceneManager, networkService);
    }

    @Override
    public void start(Stage stage) {
        networkService.start();
        eventThread.start();

        final Scene loginScene = sceneManager.getCurrentScene();
        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        networkService.stop();
        eventThread.stopProcessing();
    }
}
