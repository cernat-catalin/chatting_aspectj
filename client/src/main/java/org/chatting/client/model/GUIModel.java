package org.chatting.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GUIModel {

    private final StringProperty label = new SimpleStringProperty();

    public StringProperty label() {
        return this.label;
    }

    public String getLabel() {
        return this.label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }
}
