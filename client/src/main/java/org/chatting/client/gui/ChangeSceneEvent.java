package org.chatting.client.gui;

public class ChangeSceneEvent implements Event {

    private final SceneType sceneType;

    public ChangeSceneEvent(SceneType sceneType) {
        this.sceneType = sceneType;
    }

    @Override
    public EventType getEventType() {
        return EventType.CHANGE_PANEL;
    }

    public SceneType getSceneType() {
        return sceneType;
    }
}
