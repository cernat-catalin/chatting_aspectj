package org.chatting.client.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.chatting.client.gui.controller.ChatRoomController;
import org.chatting.client.gui.controller.LoginController;
import org.chatting.client.model.GUIModel;

public class SceneManager {
    private final EventQueue eventQueue;
    private final LoginController loginController;
    private final ChatRoomController chatRoomController;
    private final GUIModel guiModel;
    private Scene currentScene;

    public SceneManager(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
        this.guiModel = new GUIModel();
        this.loginController = new LoginController(eventQueue, guiModel);
        this.chatRoomController = new ChatRoomController(eventQueue, guiModel);
        this.currentScene = loginController.getScene();
//        this.currentScene = chatRoomController.getScene();
    }

    public GUIModel getGuiModel() {
        return guiModel;
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
