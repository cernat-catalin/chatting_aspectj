package org.chatting.client.gui.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.chatting.client.gui.EventQueue;
import org.chatting.client.gui.event.Event;
import org.chatting.client.gui.event.LoginButtonClick;
import org.chatting.client.model.GUIModel;

public class LoginController {

    private final EventQueue eventQueue;
    private final GUIModel guiModel;
    private final Scene scene;

    public LoginController(EventQueue eventQueue, GUIModel guiModel) {
        this.eventQueue = eventQueue;
        this.guiModel = guiModel;
        this.scene = generateLoginScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene generateLoginScene() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Username Field
        final Label usernameLabel = new Label("User Name:");
        grid.add(usernameLabel, 0, 1);
        final TextField usernameFiled = new TextField();
        grid.add(usernameFiled, 1, 1);

        // Password Field
        final Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        final PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // Login Button
        final Button loginButton = new Button("Log in");
        final HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        loginButton.setOnAction(e -> {
            final String username = usernameFiled.getText();
            final String password = passwordField.getText();
            final Event loginButtonClick = new LoginButtonClick(username, password);
//            final Event changeScene = new ChangeSceneEvent(SceneType.CHAT_ROOM);

            eventQueue.pushEvent(loginButtonClick);
        });

        return new Scene(grid, 400, 200);
    }
}
