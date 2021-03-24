package org.chatting.client.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SceneManager {
    private final EventQueue eventQueue;
    private final LoginController loginController;
    private final ChatRoomController chatRoomController;
    private Scene currentScene;

    public SceneManager(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
        this.loginController = new LoginController(eventQueue);
        this.chatRoomController = new ChatRoomController(eventQueue);
        this.currentScene = loginController.getScene();
    }

    public void changeScene(SceneType sceneType) {
        switch (sceneType) {
            case LOGIN:
                changeCurrentScene(loginController.getScene());
                break;
            case CHAT_ROOM:
                changeCurrentScene(chatRoomController.getScene());
                break;
        }
    }

    private void changeCurrentScene(Scene scene) {
        currentScene = scene;
        currentStage().setScene(currentScene);
    }

    public Stage currentStage() {
        return (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
