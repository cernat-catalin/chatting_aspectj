package org.chatting.client.model;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.List;

public class GUIModel {

    private ListView<String> chatMessagesListView;
    private ListView<String> connectedUsersListView;

    public ListView<String> getChatMessagesListView() {
        return chatMessagesListView;
    }

    public void setChatMessagesListView(ListView<String> chatMessagesListView) {
        this.chatMessagesListView = chatMessagesListView;
    }

    public void addChatMessage(String chatMessage) {
        chatMessagesListView.getItems().add(chatMessage);
        chatMessagesListView.scrollTo(chatMessagesListView.getItems().size() - 1);
    }

    public ListView<String> getConnectedUsersListView() {
        return connectedUsersListView;
    }

    public void setConnectedUsersListView(ListView<String> connectedUsersListView) {
        this.connectedUsersListView = connectedUsersListView;
    }

    public void setConnectedUsers(List<String> connectedUsers) {
        connectedUsersListView.setItems(FXCollections.observableArrayList(connectedUsers));
    }
}
