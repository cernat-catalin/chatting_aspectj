package org.chatting.client.gui.controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.chatting.client.gui.EventQueue;
import org.chatting.client.gui.event.Event;
import org.chatting.client.gui.event.SendButtonClick;
import org.chatting.client.model.GUIModel;

public class ChatRoomController {

    private final EventQueue eventQueue;
    private final Scene scene;
    private final GUIModel guiModel;

    public ChatRoomController(EventQueue eventQueue, GUIModel guiModel) {
        this.eventQueue = eventQueue;
        this.guiModel = guiModel;
        this.scene = generateChatRoomScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene generateChatRoomScene() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Connected Users
        final Label usersTitleLabel = new Label();
        usersTitleLabel.setText("Connected users");

        final ListView<String> usersList = new ListView<>();
        usersList.setMinHeight(495);
        usersList.setMinWidth(130);
        usersList.setMaxWidth(120);
        guiModel.setConnectedUsersListView(usersList);

        final VBox usersBox = new VBox();
        usersBox.getChildren().add(usersTitleLabel);
        usersBox.getChildren().add(usersList);
        grid.add(usersBox, 0, 0);

        // Chat
        final ListView<String> listView = new ListView<>();
        listView.setMinWidth(1000);
        listView.setMaxWidth(1000);
        listView.setMinHeight(510);
        guiModel.setChatMessagesListView(listView);

        final HBox chatBox = new HBox();
        chatBox.getChildren().add(listView);
        grid.add(chatBox, 1, 0);


        // Send text area
        final HBox sendTextBox = new HBox(10);
        final TextField sendTextField = new TextField();
        final Button sendBtn = new Button("Send");

        sendTextBox.getChildren().add(sendTextField);
        sendTextBox.getChildren().add(sendBtn);
        HBox.setHgrow(sendTextField, Priority.ALWAYS);

        grid.add(sendTextBox, 1, 1);

        sendBtn.setOnAction(e -> {
            final String text = sendTextField.getText();
            final Event sendButtonClick = new SendButtonClick(text);
            eventQueue.pushEvent(sendButtonClick);
            sendTextField.clear();
        });

        sendTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                final String text = sendTextField.getText();
                final Event sendButtonClick = new SendButtonClick(text);
                eventQueue.pushEvent(sendButtonClick);
                sendTextField.clear();
            }
        });

        return new Scene(grid, 1200, 600);
    }
}
