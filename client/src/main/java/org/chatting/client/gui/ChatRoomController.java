package org.chatting.client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ChatRoomController {

    private final EventQueue eventQueue;

    public ChatRoomController(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public Scene getScene() {
        return generateChatRoomScene();
    }

    private Scene generateChatRoomScene() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Scene scene = new Scene(grid, 800, 275);

        final TextArea textArea = new TextArea();
        grid.add(textArea, 0, 0);

        final TextField textField = new TextField();
        grid.add(textField, 0, 1);

        final Label label = new Label();
        label.setText("Some Label here");

        grid.add(label, 0, 2);

        final Button sendBtn = new Button("Send");
        final Button logoutBtn = new Button("Log out");

        final HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(sendBtn);
        hbBtn.getChildren().add(logoutBtn);
        grid.add(hbBtn, 1, 4);

        sendBtn.setOnAction(e -> {
            final String text = textField.getText();
            final Event sendButtonClick = new SendButtonClick(text);
            eventQueue.pushEvent(sendButtonClick);
            textField.clear();
        });

        logoutBtn.setOnAction(e -> {
            final Event changeSceneEvent = new ChangeSceneEvent(SceneType.LOGIN);
            eventQueue.pushEvent(changeSceneEvent);
        });

        return scene;
    }
}
