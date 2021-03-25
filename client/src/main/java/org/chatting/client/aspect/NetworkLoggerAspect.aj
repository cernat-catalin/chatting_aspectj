package org.chatting.client.aspect;

import org.chatting.common.message.Message;

import java.util.logging.Logger;

public aspect NetworkLoggerAspect {

    private static final Logger LOGGER = Logger.getLogger("NetworkMessageLogger");

    pointcut processMessage():
            execution(void org.chatting.client.network.ReadThread.processMessage(..));

    pointcut messageSent():
            execution(void org.chatting.client.network.WriteThread.sendMessage(..));

    before(Message message): processMessage() && args(message) {
        LOGGER.info(String.format("Received message of type: [%s]", message.getMessageType()));
    }

    after(Message message): messageSent() && args(message) {
        LOGGER.info(String.format("Sent message of type: [%s]", message.getMessageType()));
    }

}
