package org.chatting.client.gui;

import javafx.application.Platform;
import org.chatting.client.gui.event.*;
import org.chatting.client.network.NetworkService;
import org.chatting.common.message.LoginMessage;
import org.chatting.common.message.Message;
import org.chatting.common.message.UserSendMessage;

public class EventThread extends Thread {

    private static final int SLEEP_BETWEEN_READ_CHECKS = 100;

    private final EventQueue eventQueue;
    private final SceneManager sceneManager;
    private final NetworkService networkService;
    private boolean shouldQuit = false;

    public EventThread(EventQueue eventQueue, SceneManager sceneManager, NetworkService networkService) {
        this.eventQueue = eventQueue;
        this.sceneManager = sceneManager;
        this.networkService = networkService;
    }

    public void run() {
        try {
            while (!shouldQuit) {
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
            case LOGIN_BUTTON_CLICK:
                System.out.println("Login Button click !!!!");
                final LoginButtonClick loginButtonClick = (LoginButtonClick) event;
                final Message loginMessage = new LoginMessage(loginButtonClick.getUsername(), loginButtonClick.getPassword());
                networkService.sendMessage(loginMessage);
                break;
            case LOGIN_RESULT:
                Platform.runLater(() -> {
                    sceneManager.changeScene(SceneType.CHAT_ROOM);
                });
                break;
            case SEND_BUTTON_CLICK:
                final SendButtonClick sendButtonClick = (SendButtonClick) event;
                final String message = sendButtonClick.getTextFieldText();
                final Message userChatMessage = new UserSendMessage(message);
                networkService.sendMessage(userChatMessage);
                break;
            case CHAT_MESSAGE_RECEIVED:
                final ChatMessageReceived chatMessageReceived = (ChatMessageReceived) event;
                Platform.runLater(() -> {
                    sceneManager.getGuiModel().addChatMessage(formatMessage(chatMessageReceived));
                });
                break;
            case USER_LIST_RECEIVED:
                System.out.printf("User List received");
                final UserListReceived userListReceived = (UserListReceived) event;
                Platform.runLater(() -> {
                    sceneManager.getGuiModel().setConnectedUsers(userListReceived.getConnectedUsers());
                });
                break;
            default:
                throw new RuntimeException("Unsupported message type in processing loop. Message Type: " + event.getEventType());
        }
    }

    public void stopProcessing() {
        shouldQuit = true;
    }

    private String formatMessage(ChatMessageReceived chatMessageReceived) {
        if (chatMessageReceived.getAuthorType() == ChatMessageReceived.AuthorType.SERVER) {
            return String.format("[SERVER]: %s", chatMessageReceived.getMessage());
        } else {
            return String.format("%s: %s", chatMessageReceived.getAuthorName(), chatMessageReceived.getMessage());
        }
    }
}
