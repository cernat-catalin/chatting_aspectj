package org.chatting.client.gui;

import org.chatting.client.gui.event.Event;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {
    public final Queue<Event> queue = new LinkedList<>();

    public EventQueue() {

    }

    public void pushEvent(Event event) {
        synchronized (queue) {
            queue.add(event);
        }
    }

    public Event popEvent() {
        synchronized (queue) {
            return queue.poll();
        }
    }

    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }
}
