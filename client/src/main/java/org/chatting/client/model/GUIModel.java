package org.chatting.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.List;

public class GUIModel {

    private ListView<String> chatMessagesListView;
    private ListView<String> connectedUsersListView;
    private StringProperty loginError = new SimpleStringProperty();
    private StringProperty signupError = new SimpleStringProperty();

    public void setChatMessagesListView(ListView<String> chatMessagesListView) {
        this.chatMessagesListView = chatMessagesListView;
    }

    public void addChatMessage(String chatMessage) {
        chatMessagesListView.getItems().add(chatMessage);
        chatMessagesListView.scrollTo(chatMessagesListView.getItems().size() - 1);
    }

    public void setConnectedUsersListView(ListView<String> connectedUsersListView) {
        this.connectedUsersListView = connectedUsersListView;
    }

    public void setConnectedUsers(List<String> connectedUsers) {
        connectedUsersListView.setItems(FXCollections.observableArrayList(connectedUsers));
    }

    public StringProperty getLoginError() {
        return loginError;
    }

    public void setLoginError() {
        loginError.set("Invalid credentials");
    }

    public void clearLoginError() {
        loginError.set("");
    }

    public StringProperty getSignupError() {
        return signupError;
    }

    public void setSignupError() {
        signupError.set("Something went wrong!");
    }

    public void clearSignupError() {
        signupError.set("");
    }
}
