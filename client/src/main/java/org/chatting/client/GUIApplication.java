package org.chatting.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.chatting.client.gui.EventProcessor;
import org.chatting.client.gui.EventQueue;
import org.chatting.client.gui.SceneManager;
import org.chatting.client.network.NetworkService;

public class GUIApplication extends Application {

    private final NetworkService networkService;
    private final SceneManager sceneManager;
    private final EventProcessor eventProcessor;

    public GUIApplication() {
        final String hostname = "localhost";
        final int port = 8000;

        final EventQueue eventQueue = new EventQueue();
        this.networkService = new NetworkService(hostname, port, eventQueue);
        this.sceneManager = new SceneManager(eventQueue);
        this.eventProcessor = new EventProcessor(eventQueue, sceneManager, networkService);
    }

    @Override
    public void start(Stage stage) {
        networkService.start();
        eventProcessor.start();

        final Scene loginScene = sceneManager.getCurrentScene();
        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        networkService.stop();
        eventProcessor.stopProcessing();
    }
}
