package org.chatting.client.aspect;

import org.aspectj.lang.Signature;
import org.chatting.client.gui.event.Event;

import java.util.logging.Logger;

public aspect EventLoggingAspect {

    private static final Logger LOGGER = Logger.getLogger("EventLogger");

    pointcut eventPush():
            call(void org.chatting.client.gui.EventQueue.pushEvent(Event));

    pointcut eventProcess():
            call(void org.chatting.client.gui.EventProcessor.processEvent(Event));

    before(Event event): eventPush() && args(event) {
        Signature signature = thisEnclosingJoinPointStaticPart.getSignature();
        LOGGER.info(String.format("Event pushed: [%s] from [%s]",
                event.getEventType(),
                signature));
    }

    before(Event event): eventProcess() && args(event) {
        LOGGER.info(String.format("Event processed: [%s]", event.getEventType()));
    }
}
