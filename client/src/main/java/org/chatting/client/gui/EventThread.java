package org.chatting.client.gui;

import javafx.application.Platform;
import org.chatting.client.network.NetworkService;

public class EventThread extends Thread {

    private static final int SLEEP_BETWEEN_READ_CHECKS = 200;

    private final EventQueue eventQueue;
    private final SceneManager sceneManager;
    private final NetworkService networkService;

    public EventThread(EventQueue eventQueue, SceneManager sceneManager, NetworkService networkService) {
        this.eventQueue = eventQueue;
        this.sceneManager = sceneManager;
        this.networkService = networkService;
    }

    public void run() {
        final boolean shouldQuit = true;
        try {
            while (shouldQuit) {
                final boolean eventsAvailable = eventQueue.size() > 0;
                if (eventsAvailable) {
                    final Event event = eventQueue.popEvent();
                    processEvent(event);
                } else {
                    Thread.sleep(SLEEP_BETWEEN_READ_CHECKS);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void processEvent(Event event) {
        switch (event.getEventType()) {
            case CHANGE_PANEL:
                System.out.println("Change Scene!!!!");
                final ChangeSceneEvent changeSceneEvent = (ChangeSceneEvent) event;
                Platform.runLater(() -> {
                    sceneManager.changeScene(changeSceneEvent.getSceneType());
                });
                break;
            default:
                throw new RuntimeException("Unsupported message type in processing loop. Message Type: " + event.getEventType());
        }
    }
}
